package com.meti.app;

import com.meti.core.*;
import com.meti.iterate.Iterators;
import com.meti.iterate.ResultIterator;
import com.meti.java.JavaList;
import com.meti.java.JavaString;
import com.meti.java.NonEmptyJavaList;
import com.meti.java.NonEmptyList;

import static com.meti.core.Results.$Result;

public record Compiler(JavaString input) {
    static Result<Option<JavaString>, CompileException> compileLine(JavaString line) {
        if (line.startsWith("package ")) return Ok.apply(new None<>());
        return line.firstIndexOfSlice("import ").map(index -> {
            var collect = index.nextExclusive("import ".length()).map(s -> line.sliceFrom(s)
                    .split("\\.")
                    .collect(JavaList.asList())).unwrapOrElse(JavaList.empty());

            return collect.into(NonEmptyJavaList::from).map(names -> $Result(() -> {
                var last = names.lastIndex();
                var before = names.sliceTo(last);
                var after = names.sliceFrom(last)
                        .into(NonEmptyJavaList::from)
                        .map(NonEmptyList::first)
                        .map(Ok::<JavaString, CompileException>apply)
                        .unwrapOrElse(new Err<>(new CompileException("No name present in import.")))
                        .into(ThrowableResult::new)
                        .$();

                return before.iter().collect(JavaString.joining(new JavaString("."))).map(parent -> parent
                        .prepend(" } from ")
                        .prependOwned(after)
                        .prepend("import { "));
            })).unwrapOrElse(new Err<>(new CompileException("Insufficient values.")));
        }).unwrapOrElse(Ok.apply(new Some<>(line)));
    }

    Result<JavaString, CompileException> compile() {
        return input.split(";")
                .map(Compiler::compileLine)
                .into(ResultIterator::new)
                .flatMapInner(Iterators::fromOption)
                .collectToResult(JavaString.joining(new JavaString(";\n")))
                .mapValue(value -> value.unwrapOrElse(JavaString.empty()));
    }
}