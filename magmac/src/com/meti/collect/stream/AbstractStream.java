package com.meti.collect.stream;

import com.meti.collect.Tuple;
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
    public <R> Stream<Tuple<T, R>> cross(Supplier<Stream<R>> other) {
        return flatMap(value -> other.get().map(otherValue -> new Tuple<>(value, otherValue)));
    }

    @Override
    public <C> C foldRight(C initial, BiFunction<C, T, C> folder) {
        var current = initial;
        while (true) {
            C finalCurrent = current;
            var tuple = next()
                    .map(next -> folder.apply(finalCurrent, next))
                    .toResolvedTuple(current);

            if (tuple.a()) {
                current = tuple.b();
            } else {
                return current;
            }
        }
    }

    @Override
    public <C> Option<C> foldRightFromTwo(BiFunction<T, T, C> initial, BiFunction<C, T, C> folder) {
        return next().and(next()).map(ttTuple -> initial.apply(ttTuple.a(), ttTuple.b())).map(initial1 -> foldRight(initial1, folder));
    }

    @Override
    public <R> Stream<Tuple<T, R>> extend(Function<T, R> extender) {
        return map(value -> new Tuple<>(value, extender.apply(value)));
    }

    @Override
    public <C> C collect(Collector<T, C> collector) {
        return foldRight(collector.initial(), collector::fold);
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

                    if (nextStream.a()) {
                        current = nextStream.b();
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
