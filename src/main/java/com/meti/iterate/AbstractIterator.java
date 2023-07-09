package com.meti.iterate;

import com.meti.collect.Collector;
import com.meti.core.Option;

import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class AbstractIterator<T> implements Iterator<T> {
    @Override
    public <C> C collect(Collector<T, C> collector) {
        return foldLeft(collector.initial(), collector::foldLeft);
    }

    @Override
    public <C> C foldLeft(C initial, BiFunction<C, T, C> folder) {
        var current = initial;
        while (true) {
            var head = head();
            if (head.isPresent()) {
                current = folder.apply(current, head.unwrapOrPanic());
            } else {
                break;
            }
        }
        return current;
    }

    @Override
    public <R> Iterator<R> map(Function<T, R> mapper) {
        return new AbstractIterator<>() {
            @Override
            public Option<R> head() {
                return AbstractIterator.this.head().map(mapper);
            }
        };
    }

    @Override
    public <R> R into(Function<Iterator<T>, R> mapper) {
        return mapper.apply(this);
    }
}
