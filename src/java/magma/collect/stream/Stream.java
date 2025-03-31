package magma.collect.stream;

import magma.option.Option;
import magma.result.Result;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Stream<T> {
    <R> R foldWithInitial(R initial, BiFunction<R, T, R> folder);

    <R> Stream<R> map(Function<T, R> mapper);

    <C> C collect(Collector<T, C> collector);

    <R> Stream<R> flatMap(Function<T, Stream<R>> mapper);

    <R, X> Result<R, X> foldToResult(R initial, BiFunction<R, T, Result<R, X>> folder);

    <R> Option<R> foldMapping(Function<T, R> mapper, BiFunction<R, T, R> folder);

    Stream<T> filter(Predicate<T> predicate);

    Stream<T> concat(Stream<T> other);

    Option<T> next();

    boolean anyMatch(Predicate<T> predicate);
}
