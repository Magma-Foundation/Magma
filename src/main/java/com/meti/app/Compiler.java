package com.meti.app;

import com.meti.core.Err;
import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.core.Tuple;
import com.meti.iterate.ResultIterator;
import com.meti.java.JavaList;
import com.meti.java.JavaMap;
import com.meti.java.JavaString;
import com.meti.java.List;

import static com.meti.core.Options.$Option;

public record Compiler(JavaString input) {
    private static Result<JavaMap<JavaString, List<JavaString>>, CompileException> compileImport(
            JavaMap<JavaString, List<JavaString>> state, JavaString input) {
        return $Option(() -> {
            var importIndex = input
                    .firstIndexOfSlice("import ").$()
                    .nextExclusive("import ".length()).$();

            return Ok.<JavaMap<JavaString, List<JavaString>>, CompileException>apply(input.lastIndexOfChar('.').map(separator -> {
                var parent = input.sliceBetween(importIndex, separator);
                var child = input.sliceFrom(separator.nextExclusive().$());

                return state.insertOrMap(parent, list -> list.add(child), () -> JavaList.of(child));
            }).unwrapOrElseGet(() -> state));
        }).unwrapOrElse(new Ok<>(state));
    }

    private static Result<JavaString, CompileException> renderState(JavaMap<JavaString, List<JavaString>> state) {
        return state.iter()
                .map(Compiler::renderImport)
                .into(ResultIterator::new)
                .collectToResult(JavaString.joining(";\n"))
                .mapValue(value -> value.unwrapOrElse(JavaString.empty()));
    }

    private static Result<JavaString, CompileException> renderImport(Tuple<JavaString, List<JavaString>> key) {
        var parent = key.a();
        var children = key.b();
        return children.iter()
                .collect(JavaString.joining(", "))
                .map(joinedChildren -> Ok.<JavaString, CompileException>apply(renderValidImport(parent, joinedChildren)))
                .unwrapOrElseGet(() -> {
                    var format = "No children present of size '%d' for parent '%s'.";
                    var message = format.formatted(children.size().unwrap(), parent.unwrap());
                    return new Err<>(new CompileException(message));
                });
    }

    private static JavaString renderValidImport(JavaString parent, JavaString joinedChildren) {
        return new JavaString("import { ")
                .appendOwned(joinedChildren)
                .append(" } from ")
                .appendOwned(parent);
    }

    Result<JavaString, CompileException> compile() {
        return input.split(";")
                .foldLeftToResult(new JavaMap<>(), Compiler::compileImport)
                .mapValueToResult(Compiler::renderState);
    }
}