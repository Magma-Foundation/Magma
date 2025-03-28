package jvm.api.collect;

import magma.api.collect.Collector;
import magma.api.collect.EmptyHead;
import magma.api.collect.Head;
import magma.api.collect.SingleHead;
import magma.api.collect.Stream;
import magma.api.option.Option;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public record HeadedStream<T>(Head<T> head) implements Stream<T> {
    @Override
    public <R> R foldWithInitial(R initial, BiFunction<R, T, R> folder) {
        R current = initial;
        while (true) {
            R finalCurrent = current;
            Tuple<Boolean, R> tuple = head.next()
                    .map(inner -> folder.apply(finalCurrent, inner))
                    .toTuple(current);

            if (tuple.left()) {
                current = tuple.right();
            } else {
                break;
            }
        }

        return current;
    }

    @Override
    public <R> Stream<R> map(Function<T, R> mapper) {
        return new HeadedStream<>(() -> head.next().map(mapper));
    }

    @Override
    public <R> R collect(Collector<T, R> collector) {
        return foldWithInitial(collector.initial(), collector::fold);
    }

    @Override
    public <R> Stream<Tuple<T, R>> zip(Stream<R> other) {
        return new HeadedStream<>(() -> head.next().and(other::next));
    }

    @Override
    public Option<T> next() {
        return head.next();
    }

    @Override
    public boolean allMatch(Predicate<T> predicate) {
        return foldWithInitial(true, (aBoolean, t) -> aBoolean && predicate.test(t));
    }

    @Override
    public <R> Option<R> foldWithMapper(Function<T, R> mapper, BiFunction<R, T, R> folder) {
        return head.next().map(mapper).map(initial -> foldWithInitial(initial, folder));
    }

    @Override
    public Stream<T> filter(Predicate<T> predicate) {
        return flatMap(element -> new HeadedStream<>(predicate.test(element)
                ? new SingleHead<>(element)
                : new EmptyHead<>()));
    }

    private <R> Stream<R> flatMap(Function<T, Stream<R>> mapper) {
        return this.<Stream<R>>foldWithInitial(new HeadedStream<>(new EmptyHead<>()),
                (rStream, t) -> rStream.concat(mapper.apply(t)));
    }

    @Override
    public <R, X> Result<R, X> foldToResult(R initial, BiFunction<R, T, Result<R, X>> folder) {
        return this.<Result<R, X>>foldWithInitial(new Ok<>(initial), (current, t) -> current.flatMapValue(inner -> folder.apply(inner, t)));
    }

    @Override
    public Stream<T> concat(Stream<T> other) {
        return new HeadedStream<>(() -> head.next().or(other::next));
    }
}
