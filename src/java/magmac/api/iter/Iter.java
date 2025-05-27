package magmac.api.iter;

import magmac.api.iter.collect.Collector;
import magmac.api.result.Result;

import magmac.api.Option;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Iter<T> {
    <R, X> Result<R, X> foldToResult(R initial, BiFunction<R, T, Result<R, X>> folder);

    <R> Iter<R> map(Function<T, R> mapper);

    <R> R fold(R initial, BiFunction<R, T, R> folder);

    <C> C collect(Collector<T, C> collector);

    Iter<T> filter(Predicate<T> predicate);

    Option<T> next();

    <R> Iter<R> flatMap(Function<T, Iter<R>> mapper);

    Iter<T> concat(Iter<T> other);
}
