package com.meti.iterate;

import com.meti.collect.Collector;
import com.meti.core.None;
import com.meti.core.Option;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractIterator<T> implements Iterator<T> {
    @Override
    public <C> C collect(Collector<T, C> collector) {
        return foldLeft(collector.initial(), collector::foldLeft);
    }

    @Override
    public void forEach(Consumer<T> consumer) {
        while (true) {
            var head = head();
            if (head.isPresent()) {
                consumer.accept(head.unwrap());
            } else {
                break;
            }
        }
    }

    @Override
    public Iterator<T> take(int count) {
        return new AbstractIterator<>() {
            private int counter = 0;

            @Override
            public Option<T> head() {
                if (counter < count) {
                    counter++;
                    return AbstractIterator.this.head();
                } else {
                    return new None<>();
                }
            }
        };
    }

    @Override
    public <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
        return head().map(mapper).<Iterator<R>>map(first -> new AbstractIterator<>() {
            private Iterator<R> current = first;

            @Override
            public void forEach(Consumer<R> consumer) {

            }

            @Override
            public Option<R> head() {
                while (true) {
                    var head = current.head();
                    if (head.isPresent()) {
                        return head;
                    } else {
                        var head1 = AbstractIterator.this.head()
                                .map(mapper)
                                .toTuple(Iterators.empty());

                        if (head1.a()) {
                            current = head1.b();
                        } else {
                            return new None<>();
                        }
                    }
                }
            }
        }).unwrapOrElse(Iterators.empty());
    }

    @Override
    public Iterator<T> filter(Predicate<T> predicate) {
        return flatMap(element -> {
            if (predicate.test(element)) return Iterators.of(element);
            else return Iterators.empty();
        });
    }

    @Override
    public <C> C foldLeft(C initial, BiFunction<C, T, C> folder) {
        var current = initial;
        while (true) {
            var head = head();
            if (head.isPresent()) {
                current = folder.apply(current, head.unwrap());
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
