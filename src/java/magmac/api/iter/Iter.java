package magmac.api.iter;

import magmac.api.collect.Collector;
import magmac.api.result.Result;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Iter<T> {
    <R, X> Result<R, X> foldToResult(R initial, BiFunction<R, T, Result<R, X>> folder);

    <R> Iter<R> map(Function<T, R> mapper);

    <R> R fold(R initial, BiFunction<R, T, R> folder);

    <C> C collect(Collector<T, C> collector);

    Iter<T> filter(Predicate<T> predicate);

    Optional<T> next();
}
