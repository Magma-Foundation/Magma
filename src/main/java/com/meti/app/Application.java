package com.meti.app;

import com.meti.core.*;
import com.meti.iterate.Iterators;
import com.meti.iterate.ResultIterator;
import com.meti.java.JavaList;
import com.meti.java.JavaSet;
import com.meti.java.JavaString;
import com.meti.java.NonEmptyJavaList;

import static com.meti.core.Results.$Result;

public final class Application {
    private final Sources sourceSources;
    private final Targets targetSources;

    public Application(Sources sourceSources, Targets targetSources) {
        this.sourceSources = sourceSources;
        this.targetSources = targetSources;
    }

    private static Result<Option<JavaString>, CompileException> compile(JavaString line) {
        if (line.startsWith("package ")) return Ok.apply(new None<>());
        return line.firstIndexOfSlice("import ").map(s -> line.sliceFrom(s)
                .split("\\.")
                .collect(JavaList.asList())
                .into(NonEmptyJavaList::from)
                .map(names -> $Result(() -> {
                    var last = names.lastIndex();
                    var before = names.sliceTo(last);
                    var after = names.sliceFrom(last)
                            .into(NonEmptyJavaList::from)
                            .map(NonEmptyJavaList::first)
                            .map(Ok::<JavaString, CompileException>apply)
                            .unwrapOrElse(new Err<>(new CompileException("No name present in import.")))
                            .into(ThrowableResult::new)
                            .$();

                    return before.iter().collect(JavaString.joining(new JavaString("."))).map(parent -> parent.prepend("import { ")
                            .prependOwned(after)
                            .prepend(" } from "));
                }))
                .unwrapOrElse(new Err<>(new CompileException("Insufficient values.")))).unwrapOrElse(Ok.apply(new Some<>(line)));
    }

    Result<JavaSet<NIOTarget>, CompileException> compileAll() {
        return sourceSources.collect()
                .mapErr(CompileException::new)
                .mapValueToResult(nioSourceJavaSet -> nioSourceJavaSet.iter()
                        .map(this::compile)
                        .into(ResultIterator::new)
                        .collectToResult(JavaSet.asSet()));
    }

    private Result<NIOTarget, CompileException> compile(NIOSource source) {
        return Results.$Result(() -> {
            var package_ = source.computePackage();
            var other = source.computeName().concat(".mgs");
            var input = source.read()
                    .mapErr(CompileException::new)
                    .into(ThrowableResult::new).$();

            var output = input.split(";")
                    .map(Application::compile)
                    .into(ResultIterator::new)
                    .flatMapInner(Iterators::fromOption)
                    .collectToResult(JavaString.joining(new JavaString(";")))
                    .into(ThrowableResult::new).$()
                    .unwrapOrElse(JavaString.empty());

            var target = targetSources.resolve(package_, other)
                    .mapErr(CompileException::new)
                    .into(ThrowableResult::new)
                    .$();

            Results.throwOption(target.write(output).map(CompileException::new));
            return target;
        });
    }
}