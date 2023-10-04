package com.meti.api.collect;

import com.meti.api.iterate.Iterators;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractIterator<T> implements Iterator<T> {
    @Override
    public Iterator<T> concat(Iterator<T> other) {
        return new AbstractIterator<>() {
            @Override
            public Option<T> head() {
                var head = AbstractIterator.this.head();
                if (head.isPresent()) {
                    return head;
                } else {
                    return other.head();
                }
            }
        };
    }

    @Override
    public <R> R foldRight(R initial, BiFunction<R, T, R> mapper) {
        var current = initial;

        while (true) {
            R finalCurrent = current;
            var tuple = head()
                    .map(head -> mapper.apply(finalCurrent, head))
                    .unwrapToTuple(current);

            if (tuple.a()) {
                current = tuple.b();
            } else {
                return current;
            }
        }
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
    public Iterator<T> filter(Predicate<T> predicate) {
        return this.<Option<T>>map(value -> {
            if (predicate.test(value)) return Some.apply(value);
            else return None.apply();
        }).flatMap(Iterators::fromOption);
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
                                .unwrapToTuple(Iterators.empty());
                        if (tuple.a()) {
                            this.current = tuple.b();
                        } else {
                            return None.apply();
                        }
                    }
                }
            }
        }).unwrapOrElse(Iterators.empty());
    }

    @Override
    public <R> R collect(Collector<T, R> collector) {
        return foldRight(collector.initial(), collector::fold);
    }
}
