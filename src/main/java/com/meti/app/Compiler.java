package com.meti.app;

import com.meti.core.Err;
import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.java.JavaList;
import com.meti.java.JavaString;
import com.meti.java.NonEmptyJavaList;
import com.meti.java.String_;

import static com.meti.core.Options.$Option;

public record Compiler(String_ input) {
    private static Result<State, CompileException> compileImport(
            State state1, String_ input) {
        return $Option(() -> {
            var importIndex = input
                    .firstIndexOfSlice("import ").$()
                    .nextExclusive("import ".length()).$();

            var segments = input.sliceFrom(importIndex);

            return segments
                    .split("\\.")
                    .collect(JavaList.asList())
                    .into(NonEmptyJavaList::from)
                    .map(state1::define)
                    .map(Ok::<State, CompileException>apply)
                    .unwrapOrElseGet(() -> Err.apply(new CompileException("Parent cannot be empty.")));
        }).unwrapOrElse(new Ok<>(state1));
    }

    private static Result<String_, CompileException> renderState(State state) {
        return Ok.apply(state.root()
                .children().iter()
                .map(anImport -> anImport.render(0))
                .map(value -> value.append(";\n"))
                .map(value -> value.prepend("import "))
                .collect(JavaString.joining(JavaString.from("")))
                .unwrapOrElse(JavaString.Empty));
    }

    Result<String_, CompileException> compile() {
        return input.split(";")
                .foldLeftToResult(new State(), Compiler::compileImport)
                .mapValueToResult(Compiler::renderState);
    }
}