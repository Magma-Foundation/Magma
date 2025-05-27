package magmac.app.compile.error;

import magmac.api.Tuple2;
import magmac.api.result.Result;
import magmac.app.compile.error.error.CompileError;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public interface CompileResult<T> {
    CompileResult<T> merge(Supplier<CompileResult<T>> other, BiFunction<T, T, T> merger);

    <R> CompileResult<R> mapValue(Function<T, R> mapper);

    <R> R match(Function<T, R> whenOk, Function<CompileError, R> whenErr);

    CompileResult<T> mapErr(Function<CompileError, CompileError> mapper);

    <R> CompileResult<R> flatMapValue(Function<T, CompileResult<R>> mapper);

    <R> CompileResult<Tuple2<T, R>> and(Supplier<CompileResult<R>> supplier);

    Result<T, CompileError> result();
}
