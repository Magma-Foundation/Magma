package com.meti.app;

import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.java.JavaString;

import static com.meti.core.Options.$Option;

public record Compiler(JavaString input) {
    Result<JavaString, CompileException> compile() {
        return compileImport().unwrapOrElseGet(() -> Ok.apply(JavaString.empty()));
    }

    private Option<Result<JavaString, CompileException>> compileImport() {
        return $Option(() -> {
            var importIndex = input
                    .firstIndexOfSlice("import ").$()
                    .nextExclusive("import ".length()).$();

            return Ok.apply(input.lastIndexOfChar('.').map(separator -> {
                var parent = input.sliceBetween(importIndex, separator);
                var child = input.sliceFrom(separator.nextExclusive().$());

                return (new JavaString("import { ")
                        .appendOwned(child)
                        .append(" } from ")
                        .appendOwned(parent));
            }).unwrapOrElseGet(() -> input));
        });
    }
}