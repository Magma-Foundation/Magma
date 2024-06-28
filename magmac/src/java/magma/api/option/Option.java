package magma.api.option;

import magma.api.Tuple;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Interface representing an optional value container, which may or may not contain a value of type T.
 * This interface provides methods to manipulate and retrieve the value, if present, or handle the absence of a value.
 *
 * @param <T> the type of the value that may be present
 */
public interface Option<T> {

    /**
     * Applies a function to the contained value if it is present, returning a new Option with the result.
     *
     * @param <R>    the type of the result
     * @param mapper the function to apply to the value
     * @return a new Option containing the result of applying the function, or an empty Option if the value is not present
     */
    <R> Option<R> map(Function<T, R> mapper);

    /**
     * Returns the contained value if present, otherwise returns the result produced by the provided Supplier.
     *
     * @param supplier the Supplier to provide a value if the contained value is not present
     * @return the contained value or the value provided by the Supplier
     */
    T orElseGet(Supplier<T> supplier);

    /**
     * Returns the contained value if present, otherwise returns the provided default value.
     *
     * @param other the default value to return if the contained value is not present
     * @return the contained value or the provided default value
     */
    T orElse(T other);

    /**
     * Checks if a value is present in this Option.
     *
     * @return true if a value is present, false otherwise
     */
    boolean isPresent();

    /**
     * Converts the Option to a Tuple containing a boolean indicating presence and the contained value or a provided default.
     *
     * @param other the default value to use if the contained value is not present
     * @return a Tuple containing a boolean and the contained or default value
     */
    Tuple<Boolean, T> toTuple(T other);

    /**
     * Checks if the Option is empty (i.e., no value is present).
     *
     * @return true if no value is present, false otherwise
     */
    boolean isEmpty();

    /**
     * Applies a function to the contained value if present, which returns another Option, and returns that Option.
     *
     * @param <R>    the type of the result
     * @param mapper the function to apply to the value
     * @return the Option returned by applying the function, or an empty Option if the value is not present
     */
    <R> Option<R> flatMap(Function<T, Option<R>> mapper);

    /**
     * Returns the contained value if present, otherwise throws an exception.
     *
     * @return the contained value
     * @throws RuntimeException if no value is present
     */
    T orElsePanic();

    /**
     * Combines this Option with another Option, returning a new Option containing a Tuple of the two values if both are present.
     *
     * @param <R>   the type of the other Option's value
     * @param other the other Option to combine with
     * @return a new Option containing a Tuple of the two values if both are present, or an empty Option if either is not present
     */
    <R> Option<Tuple<T, R>> and(Option<R> other);
}
