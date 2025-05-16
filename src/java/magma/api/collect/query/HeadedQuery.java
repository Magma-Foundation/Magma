package magma.api.collect.query;

import magma.api.Tuple2;
import magma.api.collect.Collector;
import magma.api.collect.EmptyHead;
import magma.api.collect.Head;
import magma.api.collect.SingleHead;
import magma.api.option.Option;
import magma.app.Main;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public record HeadedQuery<T>(Head<T> head) implements Query<T> {
    @Override
    public Option<T> next() {
        return this.head.next();
    }

    @Override
    public <C> C collect(Collector<T, C> collector) {
        return this.foldWithInitial(collector.createInitial(), collector::fold);
    }

    @Override
    public <R> Query<R> map(Function<T, R> mapper) {
        return new HeadedQuery<R>(new Main.MapHead<T, R>(this.head, mapper));
    }

    @Override
    public <R> R foldWithInitial(R initial, BiFunction<R, T, R> folder) {
        R result = initial;
        while (true) {
            R finalResult = result;
            Tuple2<Boolean,R> maybeNext = this.head.next()
                    .map((T inner) -> folder.apply(finalResult, inner))
                    .toTuple(finalResult);

            if (maybeNext.left()) {
                result = maybeNext.right();
            }
            else {
                return result;
            }
        }
    }

    @Override
    public <R> Option<R> foldWithMapper(Function<T, R> next, BiFunction<R, T, R> folder) {
        return this.head.next().map(next).map((R maybeNext) -> {
            return this.foldWithInitial(maybeNext, folder);
        });
    }

    @Override
    public <R> Query<R> flatMap(Function<T, Query<R>> mapper) {
        return this.head.next()
                .map(mapper)
                .map((Query<R> initial) -> new HeadedQuery<R>(new Main.FlatMapHead<T, R>(this.head, initial, mapper)))
                .orElseGet(() -> new HeadedQuery<R>(new EmptyHead<R>()));
    }

    @Override
    public boolean allMatch(Predicate<T> predicate) {
        return this.foldWithInitial(true, (Boolean maybeAllTrue, T element) -> maybeAllTrue && predicate.test(element));
    }

    @Override
    public Query<T> filter(Predicate<T> predicate) {
        return this.flatMap((T element) -> {
            if (predicate.test(element)) {
                return new HeadedQuery<T>(new SingleHead<T>(element));
            }
            else {
                return new HeadedQuery<T>(new EmptyHead<T>());
            }
        });
    }
}
