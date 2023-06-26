package com.meti.safe.iter;

import com.meti.safe.Tuple2;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractIterator<T> implements Iterator<T> {
    @Override
    public <R, E extends Throwable> ResultIter<R, E> mapToResult(Function<T, Result<R, E>> mapper) {
        return new ResultIter<>(map(mapper));
    }

    @Override
    public <R, E extends Throwable> ResultIter<R, E> flatMapToResult(Function<T, Iterator<Result<R, E>>> mapper) {
        return new ResultIter<>(flatMap(mapper));
    }

    @Override
    public boolean anyMatch(Predicate<T> predicate) {
        return filter(predicate).head().isPresent();
    }

    @Override
    public <R> R collect(Collector<R, T> collector) {
        return foldLeft(collector.initial(), collector::folder);
    }

    @Override
    public boolean allMatch(Predicate<T> predicate) {
        return filter(predicate.negate()).head().isEmpty();
    }

    @Override
    public Iterator<T> filter(Predicate<T> predicate) {
        return flatMap(value -> Iterators.fromOption(Some.apply(value).filter(predicate)));
    }

    @Override
    public <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
        return new AbstractIterator<>() {
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
                        return None.apply();
                    }
                }

                do {
                    var head = current.head();
                    if (head.isEmpty()) {
                        var map = AbstractIterator.this.head().map(mapper);
                        if (map.isPresent()) {
                            current = map.unwrapOrPanic();
                        } else {
                            return None.apply();
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
        return new AbstractIterator<>() {
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
        return foldLeft(Ok.apply(state), (accumulated, t) -> accumulated.flatMapValue(r -> folder.apply(r, t)));
    }
}
