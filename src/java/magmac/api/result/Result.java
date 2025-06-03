package magmac.api.result;

import magmac.api.Tuple2;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents either a successful value or an error value.
 */

public interface Result<T, X> {
    /**
     * Maps the success value using {@code mapper}.
     */
    <R> Result<R, X> mapValue(Function<T, R> mapper);

    /**
     * Combines this result with another result produced by {@code supplier}.
     */
    <R> Result<Tuple2<T, R>, X> and(Supplier<Result<R, X>> supplier);

    /**
     * Executes one of the functions depending on whether this is ok or err.
     */
    <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

    /**
     * Maps to another result and flattens the output.
     */
    <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);

    /**
     * Maps the error value using {@code mapper}.
     */
    <R> Result<T, R> mapErr(Function<X, R> mapper);
}
