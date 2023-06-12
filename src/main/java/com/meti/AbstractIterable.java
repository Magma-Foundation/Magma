package com.meti;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractIterable<T> implements Iterable<T> {
    @Override
    public boolean allMatch(Predicate<T> predicate) {
        return filter(predicate.negate()).head().isEmpty();
    }

    @Override
    public Option<Iterable<T>> take(int count) {
        if (count >= 0) {
            for (int i = 0; i < count; i++) {
                head();
            }
            return Options.Some(this);
        } else {
            return Options.None();
        }
    }

    @Override
    public Iterable<T> filter(Predicate<T> filter) {
        return this.flatMap(t -> filter.test(t)
                ? Iterables.of(t)
                : Iterables.empty());
    }

    @Override
    public <R> Iterable<Tuple<T, R>> zip(Iterable<R> iter) {
        return new AbstractIterable<>() {
            @Override
            public Option<Tuple<T, R>> head() {
                return AbstractIterable.this.head().flatMap(
                        head -> iter.head().map(
                                otherHead -> new Tuple<>(head, otherHead)));
            }
        };
    }

    @Override
    public <R> Iterable<R> flatMap(Function<T, Iterable<R>> mapper) {
        return new AbstractIterable<>() {
            private Option<T> currentValue = AbstractIterable.this.head();
            private Option<Iterable<R>> currentIterable = currentValue.map(mapper);

            @Override
            public Option<R> head() {
                while (true) {
                    if (currentIterable.isPresent()) {
                        var iterable = currentIterable.unwrapOrPanic();
                        var next = iterable.head();
                        if (next.isPresent()) {
                            return next;
                        } else {
                            var value = advance();
                            if (!value) return Options.None();
                        }
                    } else {
                        var value = advance();
                        if (!value) return Options.None();
                    }
                }
            }

            private boolean advance() {
                currentValue = AbstractIterable.this.head();
                if (currentValue.isPresent()) {
                    currentIterable = currentValue.map(mapper);
                    return true;
                } else {
                    return false;
                }
            }
        };
    }

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
