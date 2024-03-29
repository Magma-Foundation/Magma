package com.meti.collect.stream;

import com.meti.collect.Pair;
import com.meti.collect.option.Option;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.meti.collect.option.None.None;

public abstract class AbstractStream<T> implements Stream<T> {
    @Override
    public <R> Stream<R> two(BiFunction<T, T, R> mapper, Function<T, R> remaining) {
        return new AbstractStream<R>() {
            @Override
            public Option<R> next() {
                return AbstractStream.this.next().map(first -> AbstractStream.this.next()
                        .map(second -> mapper.apply(first, second))
                        .orElseGet(() -> remaining.apply(first)));
            }
        };
    }

    @Override
    public Stream<T> concat(Stream<T> other) {
        return new AbstractStream<>() {
            @Override
            public Option<T> next() {
                return AbstractStream.this.next().or(other::next);
            }
        };
    }

    @Override
    public <R> Stream<Pair<T, R>> cross(Supplier<Stream<R>> other) {
        return flatMap(value -> other.get().map(otherValue -> new Pair<>(value, otherValue)));
    }

    @Override
    public <C> C foldRightFromInitial(C initial, BiFunction<C, T, C> folder) {
        var current = initial;
        while (true) {
            C finalCurrent = current;
            var tuple = next()
                    .map(next -> folder.apply(finalCurrent, next))
                    .toResolvedTuple(current);

            if (tuple.left()) {
                current = tuple.right();
            } else {
                return current;
            }
        }
    }

    @Override
    public <C> Option<C> foldRightFromTwo(BiFunction<T, T, C> initial, BiFunction<C, T, C> folder) {
        var first = next();
        var second = next();
        return first.and(second).map(ttTuple -> initial.apply(ttTuple.left(), ttTuple.right())).map(initial1 -> foldRightFromInitial(initial1, folder));
    }

    @Override
    public <C> Option<C> foldRight(Function<T, C> mapper, BiFunction<C, T, C> folder) {
        return next().map(head -> foldRightFromInitial(mapper.apply(head), folder));
    }

    @Override
    public <R> Stream<Pair<T, R>> extend(Function<T, R> extender) {
        return map(value -> new Pair<>(value, extender.apply(value)));
    }

    @Override
    public <C> C collect(Collector<T, C> collector) {
        return foldRightFromInitial(collector.initial(), collector::fold);
    }

    @Override
    public Stream<T> filter(Predicate<T> predicate) {
        return flatMap(element -> predicate.test(element) ? Streams.from(element) : Streams.empty());
    }

    @Override
    public <R> Stream<R> flatMap(Function<T, Stream<R>> mapper) {
        return next().map(mapper).<Stream<R>>map(initial -> new AbstractStream<R>() {
            private Stream<R> current = initial;

            @Override
            public Option<R> next() {
                while (true) {
                    var next = current.next();
                    if (next.isPresent()) return next;

                    var nextStream = AbstractStream.this.next()
                            .map(mapper)
                            .toResolvedTuple(current);

                    if (nextStream.left()) {
                        current = nextStream.right();
                    } else {
                        return None();
                    }
                }
            }
        }).orElse(Streams.empty());
    }

    @Override
    public <R> Stream<R> map(Function<T, R> mapper) {
        return new AbstractStream<>() {
            @Override
            public Option<R> next() {
                return AbstractStream.this.next().map(mapper);
            }
        };
    }
}
