package com.meti.app;

import com.meti.core.*;
import com.meti.iterate.Iterators;
import com.meti.iterate.ResultIterator;
import com.meti.java.JavaSet;
import com.meti.java.JavaString;

import java.io.IOException;

public final class Application {
    private final Sources sourceSources;
    private final Targets targetSources;

    public Application(Sources sourceSources, Targets targetSources) {
        this.sourceSources = sourceSources;
        this.targetSources = targetSources;
    }

    Result<JavaSet<NIOTarget>, IOException> compileAll() {
        return sourceSources.collect().mapValueToResult(s -> s.iter()
                .map(this::compile)
                .into(ResultIterator::new)
                .collectToResult(JavaSet.asSet()));
    }

    private Result<NIOTarget, IOException> compile(NIOSource source) {
        var package_ = source.computePackage();
        var other = source.computeName().concat(".mgs");
        return source.read().mapValueToResult(input -> {
            var output = input.split(";")
                    .map(line -> line.startsWith("package ") ? new None<JavaString>() : new Some<>(line))
                    .flatMap(Iterators::fromOption)
                    .collect(JavaString.joining(new JavaString(";")))
                    .unwrapOrElse(JavaString.empty());

            return targetSources.resolve(package_, other).mapValueToResult(target -> target.write(output)
                    .map(Err::<NIOTarget, IOException>apply)
                    .unwrapOrElse(new Ok<>(target)));
        });
    }
}