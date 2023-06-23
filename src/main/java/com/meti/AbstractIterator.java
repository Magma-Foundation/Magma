package com.meti;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractIterator<T> implements Iterator<T> {
    @Override
    public boolean allMatch(Predicate<T> predicate) {
        return filter(predicate.negate()).head().isEmpty();
    }

    @Override
    public Iterator<T> filter(Predicate<T> predicate) {
        return flatMap(value -> Iterators.fromOption(new Some<>(value).filter(predicate)));
    }

    @Override
    public <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
        return new AbstractIterator<R>() {
            private Iterator<R> current;

            @Override
            public Option<R> head() {
                /*
                TODO: remove this duplicate
                 */
                if (current == null) {
                    var map = AbstractIterator.this.head().map(mapper);
                    if (map.isPresent()) {
                        current = map.unwrapOrPanic();
                    } else {
                        return new None<>();
                    }
                }

                do {
                    var head = current.head();
                    if (head.isEmpty()) {
                        var map = AbstractIterator.this.head().map(mapper);
                        if (map.isPresent()) {
                            current = map.unwrapOrPanic();
                        } else {
                            return new None<>();
                        }
                    } else {
                        return head;
                    }
                } while (true);
            }
        };
    }

    @Override
    public <R> Iterator<Tuple2<T, R>> zip(Iterator<R> other) {
        return new AbstractIterator<>() {
            @Override
            public Option<Tuple2<T, R>> head() {
                return AbstractIterator.this.head().and(other.head());
            }
        };
    }

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
    public <R> R foldLeft(R state, BiFunction<R, T, R> folder) {
        var current = state;
        while (true) {
            var head = head();
            if (head.isPresent()) {
                current = folder.apply(current, head.unwrapOrPanic());
            } else {
                return current;
            }
        }
    }

    @Override
    public <R, E extends Throwable> Result<R, E> foldLeftResult(R state, BiFunction<R, T, Result<R, E>> folder) {
        return foldLeft(Ok.apply(state), (accumulated, t) -> accumulated.mapValueToResult(r -> folder.apply(r, t)));
    }
}
