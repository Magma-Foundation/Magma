package magmac.api;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Functional container for an optional value.
 */

public interface Option<T> {
    /**
     * Transforms the contained value if present.
     *
     * @param mapper function to apply to the inner value
     * @param <R>    the resulting type
     * @return an {@code Option} containing the mapped value or empty
     */
    <R> Option<R> map(Function<T, R> mapper);

    /**
     * Checks whether a value is present.
     *
     * @return {@code true} when a value exists
     */
    boolean isPresent();

    /**
     * Returns the value if present or obtains a fallback.
     *
     * @param other supplier used when the option is empty
     * @return the contained value or the supplier result
     */
    T orElseGet(Supplier<T> other);

    /**
     * Checks whether no value is present.
     *
     * @return {@code true} when empty
     */
    boolean isEmpty();

    /**
     * Maps the value to another {@code Option} if present and flattens the result.
     *
     * @param mapper mapping function returning an option
     * @param <R>    the resulting type
     * @return the mapped option or empty
     */
    <R> Option<R> flatMap(Function<T, Option<R>> mapper);

    /**
     * Returns the value if present or a default.
     *
     * @param other fallback value
     * @return the contained value or {@code other}
     */
    T orElse(T other);

    /**
     * Keeps the value only when it satisfies the predicate.
     *
     * @param predicate test applied to the value
     * @return this option when the test passes otherwise an empty option
     */
    Option<T> filter(Predicate<T> predicate);

    /**
     * Uses the supplied option when empty.
     *
     * @param other supplier of an alternative option
     * @return this option or the supplied one when empty
     */
    Option<T> or(Supplier<Option<T>> other);

    /**
     * Invokes the consumer when a value is present.
     *
     * @param consumer action to perform on the contained value
     */
    void ifPresent(Consumer<T> consumer);
}
