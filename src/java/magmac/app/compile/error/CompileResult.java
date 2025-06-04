package magmac.app.compile.error;

import magmac.api.Tuple2;
import magmac.api.result.Result;
import magmac.app.compile.error.type.CompileError;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Outcome of a compilation step which may hold a value or a {@link CompileError}.
 *
 * @param <T> type of the successful value
 */
public interface CompileResult<T> {
    /**
     * Merges this result with another result using a value merger.
     */
    CompileResult<T> merge(Supplier<CompileResult<T>> other, BiFunction<T, T, T> merger);

    /**
     * Maps the successful value using {@code mapper}.
     */
    <R> CompileResult<R> mapValue(Function<T, R> mapper);

    /**
     * Applies one of the functions depending on success or error.
     */
    <R> R match(Function<T, R> whenOk, Function<CompileError, R> whenErr);

    /**
     * Maps the contained error using {@code mapper}.
     */
    CompileResult<T> mapErr(Function<CompileError, CompileError> mapper);

    /**
     * Maps to another result and flattens the output.
     */
    <R> CompileResult<R> flatMapValue(Function<T, CompileResult<R>> mapper);

    /**
     * Combines this result with the supplied one.
     */
    <R> CompileResult<Tuple2<T, R>> and(Supplier<CompileResult<R>> supplier);

    /**
     * Converts this compile result to a generic {@link Result}.
     */
    Result<T, CompileError> toResult();
}
