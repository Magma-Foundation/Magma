package com.meti.app;

import com.meti.core.Err;
import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.core.Tuple;
import com.meti.java.*;

import static com.meti.core.Options.$Option;

public record Compiler(String_ input) {
    private static Result<State, CompileException> compileImport(
            State state1, String_ input) {
        return $Option(() -> {
            var importIndex = input
                    .firstIndexOfSlice("import ").$()
                    .nextExclusive("import ".length()).$();

            return input.sliceFrom(importIndex)
                    .split("\\.")
                    .collect(JavaList.asList())
                    .into(NonEmptyJavaList::from)
                    .map(state1::define)
                    .map(Ok::<State, CompileException>apply)
                    .unwrapOrElseGet(() -> Err.apply(new CompileException("Parent cannot be empty.")));
        }).unwrapOrElse(new Ok<>(state1));
    }

    private static Result<String_, CompileException> renderState(State state) {
        return Ok.apply(JavaString.empty());
    }

    private static Result<String_, CompileException> renderImport(Tuple<String_, List<String_>> key) {
        var parent = key.a();
        var children = key.b();
        return children.iter()
                .collect(JavaString.joining(JavaString.from(", ")))
                .map(joinedChildren -> Ok.<String_, CompileException>apply(renderValidImport(parent, joinedChildren)))
                .unwrapOrElseGet(() -> {
                    var format = "No children present of size '%d' for parent '%s'.";
                    var message = format.formatted(children.size().unwrap(), parent.unwrap());
                    return new Err<>(new CompileException(message));
                });
    }

    private static String_ renderValidImport(String_ parent, String_ joinedChildren) {
        return JavaString.from("import { ")
                .appendOwned(joinedChildren)
                .append(" } from ")
                .appendOwned(parent);
    }

    Result<String_, CompileException> compile() {
        return input.split(";")
                .foldLeftToResult(new State(), Compiler::compileImport)
                .mapValueToResult(Compiler::renderState);
    }
}