package com.meti;

import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class AbstractIterable<T> implements Iterable<T> {

    @Override
    public <R> Iterable<R> map(Function<T, R> mapper) {
        return new AbstractIterable<>() {
            @Override
            public Option<R> head() {
                return AbstractIterable.this.head().map(mapper);
            }
        };
    }

    @Override
    public <R> R foldLeft(R initial, BiFunction<R, T, R> folder) {
        var current = initial;
        while (true) {
            var value = head();
            if (value.isPresent()) {
                current = folder.apply(current, value.unwrapOrPanic());
            } else {
                return current;
            }
        }
    }

    @Override
    public <R> R collect(Collector<R, T> collector) {
        return foldLeft(collector.initial(), collector::fold);
    }
}
