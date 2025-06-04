package magmac.api.iter;

import magmac.api.Option;
import magmac.api.iter.collect.Collector;
import magmac.api.result.Result;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Lazy sequence of elements with common operations.
 */

public interface Iter<T> {
    /**
     * Folds elements with a function that may produce a result.
     */
    <R, X> Result<R, X> foldToResult(R initial, BiFunction<R, T, Result<R, X>> folder);

    /**
     * Lazily maps each element using {@code mapper}.
     */
    <R> Iter<R> map(Function<T, R> mapper);

    /**
     * Folds elements from left to right.
     */
    <R> R fold(R initial, BiFunction<R, T, R> folder);

    /**
     * Collects elements using a provided collector.
     */
    <C> C collect(Collector<T, C> collector);

    /**
     * Retains only elements that satisfy the predicate.
     */
    Iter<T> filter(Predicate<T> predicate);

    /**
     * Retrieves the next element or returns empty when exhausted.
     */
    Option<T> next();

    /**
     * Maps each element to another iterator and flattens the result.
     */
    <R> Iter<R> flatMap(Function<T, Iter<R>> mapper);

    /**
     * Appends another iterator lazily.
     */
    Iter<T> concat(Iter<T> other);
}
