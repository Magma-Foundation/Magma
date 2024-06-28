package magma.api.collect.stream;

import magma.api.Tuple;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.java.JavaOptionals;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public record HeadedStream<T>(Head<T> provider) implements Stream<T> {
    @Override
    public <R, E> Result<R, E> foldLeftToResult(R initial, BiFunction<R, T, Result<R, E>> mapper) {
        return this.foldLeft(Ok.from(initial),
                (reResult, t) -> reResult.flatMapValue(
                        inner -> mapper.apply(inner, t)));
    }

    @Override
    public boolean anyMatch(Predicate<T> predicate) {
        return foldLeft(false, (aBoolean, t) -> aBoolean || predicate.test(t));
    }

    @Override
    public Stream<T> filter(Predicate<T> filter) {
        return flatMap(value -> new HeadedStream<>(filter.test(value)
                ? new SingleHead<>(value)
                : new EmptyHead<>()));
    }

    @Override
    public <R> Stream<R> flatMap(Function<T, Head<R>> mapper) {
        return new HeadedStream<>(head()
                .map(mapper)
                .<Head<R>>map(initial -> new FlatMapHead<>(initial, this, mapper))
                .orElse(new EmptyHead<>()));
    }

    @Override
    public <R> Stream<Tuple<T, R>> extend(Function<T, R> mapper) {
        return map(value -> new Tuple<>(value, mapper.apply(value)));
    }

    @Override
    public <R> Stream<R> map(Function<T, R> mapper) {
        return new HeadedStream<>(() -> this.provider.head().map(mapper));
    }

    @Override
    public <C> C collect(Collector<T, C> collector) {
        var current = collector.createInitial();
        return foldLeft(current, collector::fold);
    }

    @Override
    public <C> C foldLeft(C current, BiFunction<C, T, C> folder) {
        while (true) {
            C finalCurrent = current;
            var tuple = JavaOptionals.toTuple(JavaOptionals.toNative(head()).map(head -> folder.apply(finalCurrent, head)), current);
            if (tuple.left()) {
                current = tuple.right();
            } else {
                return current;
            }
        }
    }

    @Override
    public Option<T> head() {
        return provider.head();
    }

    private class FlatMapHead<R> implements Head<R> {
        private final Head<T> outer;
        private final Function<T, Head<R>> mapper;
        private Head<R> current;

        public FlatMapHead(Head<R> initial, HeadedStream<T> outer, Function<T, Head<R>> mapper) {
            this.outer = outer;
            this.mapper = mapper;
            current = initial;
        }

        @Override
        public Option<R> head() {
            while (true) {
                var currentHead = current.head();
                if (currentHead.isPresent()) return currentHead;

                var tuple = outer.head()
                        .map(mapper)
                        .toTuple(current);

                if (tuple.left()) {
                    current = tuple.right();
                } else {
                    return new None<>();
                }
            }
        }
    }
}
