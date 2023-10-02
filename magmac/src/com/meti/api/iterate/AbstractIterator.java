package com.meti.api.iterate;

import com.meti.api.option.None;
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
    public <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
        return head().map(mapper).<Iterator<R>>map(initial -> {
            return new AbstractIterator<R>() {
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
                                    .unwrapToTuple(Iterators.empty());
                            if (tuple.a()) {
                                this.current = tuple.b();
                            } else {
                                return None.apply();
                            }
                        }
                    }
                }
            };
        }).unwrapOrElse(Iterators.empty());
    }
}
