package magma.api.collect.head;

import magma.api.Tuple2;
import magma.api.collect.Collector;
import magma.api.collect.Iter;
import magma.api.option.Option;
import magma.api.result.Ok;
import magma.api.result.Result;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public record HeadedIter<T>(Head<T> head) implements Iter<T> {
    @Override
    public Option<T> next() {
        return this.head.next();
    }

    @Override
    public <C> C collect(Collector<T, C> collector) {
        return this.foldWithInitial(collector.createInitial(), collector::fold);
    }

    @Override
    public <R> Iter<R> map(Function<T, R> mapper) {
        return new HeadedIter<R>(new MapHead<T, R>(this.head, mapper));
    }

    @Override
    public <R> R foldWithInitial(R initial, BiFunction<R, T, R> folder) {
        R result = initial;
        while (true) {
            R finalResult = result;
            Tuple2<Boolean, R> maybeNext = this.head.next()
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
        return this.head.next().map(next).map((R maybeNext) -> this.foldWithInitial(maybeNext, folder));
    }

    @Override
    public <R> Iter<R> flatMap(Function<T, Iter<R>> mapper) {
        return this.head.next()
                .map(mapper)
                .map((Iter<R> initial) -> new HeadedIter<R>(new FlatMapHead<T, R>(this.head, initial, mapper)))
                .orElseGet(() -> new HeadedIter<R>(new EmptyHead<R>()));
    }

    @Override
    public boolean allMatch(Predicate<T> predicate) {
        return this.foldWithInitial(true, (Boolean maybeAllTrue, T element) -> maybeAllTrue && predicate.test(element));
    }

    @Override
    public boolean anyMatch(Predicate<T> predicate) {
        return this.foldWithInitial(false, (Boolean aBoolean, T t) -> aBoolean || predicate.test(t));
    }

    @Override
    public <R, X> Result<R, X> foldWithInitialToResult(R initial, BiFunction<R, T, Result<R, X>> folder) {
        return this.foldWithInitial(new Ok<R, X>(initial),
                (Result<R, X> rxResult, T element) -> rxResult.flatMapValue(
                        (R inner) -> folder.apply(inner, element)));
    }

    @Override
    public Iter<T> filter(Predicate<T> predicate) {
        return this.flatMap((T element) -> {
            if (predicate.test(element)) {
                return new HeadedIter<T>(new SingleHead<T>(element));
            }
            else {
                return new HeadedIter<T>(new EmptyHead<T>());
            }
        });
    }
}
