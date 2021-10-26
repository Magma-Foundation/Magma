package com.meti.app.compile;

import com.meti.app.clang.Processor;

import java.util.stream.Stream;

public abstract class AbstractStage<A, B extends Processor<A>> {
    public A process() {
        return createProcessors()
                .map(Processor::process)
                .map(option -> option.map(Stream::of))
                .flatMap(option -> option.orElse(Stream.empty()))
                .findFirst()
                .orElseThrow();
    }

    protected abstract Stream<B> createProcessors();
}
