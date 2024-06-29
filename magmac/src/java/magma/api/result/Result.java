package magma.api.result;

import magma.api.option.Option;

import java.util.function.Function;

/**
 * Interface representing a result of an operation that can either be a success (containing a value of type T)
 * or a failure (containing an error of type E).
 *
 * @param <T> the type of the value contained in a successful result
 * @param <E> the type of the error contained in a failed result
 */
public interface Result<T, E> {

    /**
     * Retrieves the value if the result is successful, wrapped in an Option.
     *
     * @return an Option containing the value if successful, or an empty Option if there is an error
     */
    Option<T> findValue();

    /**
     * Retrieves the error if the result is a failure, wrapped in an Option.
     *
     * @return an Option containing the error if failed, or an empty Option if there is a value
     */
    Option<E> findErr();

    /**
     * Applies a function to the contained value if the result is successful, which returns another Result,
     * and returns that Result.
     *
     * @param <R>    the type of the new Result's value
     * @param mapper the function to apply to the value
     * @return the Result returned by applying the function, or the original error if the result is a failure
     */
    <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper);

    /**
     * Applies a function to the contained value if the result is successful, returning a new Result with the transformed value.
     *
     * @param <R>    the type of the transformed value
     * @param mapper the function to apply to the value
     * @return a new Result containing the transformed value, or the original error if the result is a failure
     */
    <R> Result<R, E> mapValue(Function<T, R> mapper);

    /**
     * Checks if the result is successful (contains a value).
     *
     * @return true if the result is successful, false if it contains an error
     */
    boolean isOk();

    /**
     * Applies a function to the contained error if the result is a failure, returning a new Result with the transformed error.
     *
     * @param <R>    the type of the transformed error
     * @param mapper the function to apply to the error
     * @return a new Result containing the transformed error, or the original value if the result is successful
     */
    <R> Result<T, R> mapErr(Function<E, R> mapper);

    /**
     * Matches the result, applying one of two functions depending on whether it is successful or failed,
     * and returns the result of the applied function.
     *
     * @param <R>   the type of the result of the applied function
     * @param onOk  the function to apply if the result is successful
     * @param onErr the function to apply if the result is a failure
     * @return the result of applying the appropriate function
     */
    <R> R match(Function<T, R> onOk, Function<E, R> onErr);
}