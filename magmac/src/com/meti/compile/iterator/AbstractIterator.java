package com.meti.compile.iterator;

import com.meti.compile.option.None;
import com.meti.compile.option.Option;

import java.util.function.BiFunction;
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
    public <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
        return head().map(mapper).<Iterator<R>>map(initial -> new AbstractIterator<>() {
            private Iterator<R> current = initial;

            @Override
            public Option<R> head() {
                while (true) {
                    var head = current.head();
                    if (head.isPresent()) {
                        return head;
                    } else {
                        var tuple = AbstractIterator.this.head()
                                .map(mapper)
                                .unwrapToTuple(current);

                        if (tuple.a()) {
                            current = tuple.b();
                        } else {
                            return None.apply();
                        }
                    }
                }
            }
        }).unwrapOrElse(new AbstractIterator<>() {
            @Override
            public Option<R> head() {
                return None.apply();
            }
        });
    }

    @Override
    public <R> R collect(Collector<T, R> collector) {
        return foldRight(collector.initial(), collector::fold);
    }

    @Override
    public <R> R foldRight(R initial, BiFunction<R, T, R> folder) {
        var current = initial;
        while (true) {
            var finalCurrent = current;
            var tuple = head()
                    .map(head -> folder.apply(finalCurrent, head))
                    .unwrapToTuple(current);
            if (tuple.a()) {
                current = tuple.b();
            } else {
                return current;
            }
        }
    }
}
