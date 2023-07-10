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
    private final Sources sources;
    private final Targets targets;

    public Application(Sources sources, Targets targets) {
        this.sources = sources;
        this.targets = targets;
    }

    private static Result<Option<JavaString>, CompileException> compile(JavaString line) {
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
                        .map(NonEmptyJavaList::first)
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

    Result<JavaSet<NIOTarget>, CompileException> compileAll() {
        return sources.collect()
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
                    .collectToResult(JavaString.joining(new JavaString(";\n")))
                    .into(ThrowableResult::new).$()
                    .unwrapOrElse(JavaString.empty());

            var target = targets.resolve(package_, other)
                    .mapErr(CompileException::new)
                    .into(ThrowableResult::new)
                    .$();

            Results.throwOption(target.write(output).map(CompileException::new));
            return target;
        });
    }
}