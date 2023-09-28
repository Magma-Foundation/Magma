package com.meti.api.collect;

import com.meti.api.option.Option;

import java.util.function.Function;

public abstract class AbstractIterator<T> implements Iterator<T> {
    @Override
    public <R> Iterator<R> map(Function<T, R> mapper) {
        return new AbstractIterator<R>() {
            @Override
            public Option<R> head() {
                return AbstractIterator.this.head().map(mapper);
            }
        };
    }

    @Override
    public <R> R collect(Collector<T, R> collector) {
        var current = collector.initial();

        while (true) {
            R finalCurrent = current;
            var tuple = head()
                    .map(head -> collector.fold(finalCurrent, head))
                    .unwrapToTuple(current);

            if (tuple.a()) {
                current = tuple.b();
            } else {
                return current;
            }
        }
    }
}
