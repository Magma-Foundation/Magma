package magma.api.collect;

import magma.api.option.Option;
import magma.api.result.Result;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Query<T> {
    <C> C collect(Collector<T, C> collector);

    <R> Query<R> map(Function<T, R> mapper);

    <R> R foldWithInitial(R initial, BiFunction<R, T, R> folder);

    <R> Option<R> foldWithMapper(Function<T, R> mapper, BiFunction<R, T, R> folder);

    <R> Query<R> flatMap(Function<T, Query<R>> mapper);

    Option<T> next();

    boolean allMatch(Predicate<T> predicate);

    Query<T> filter(Predicate<T> predicate);

    boolean anyMatch(Predicate<T> predicate);

    <R, X> Result<R, X> foldWithInitialToResult(R initial, BiFunction<R, T, Result<R, X>> folder);
}
