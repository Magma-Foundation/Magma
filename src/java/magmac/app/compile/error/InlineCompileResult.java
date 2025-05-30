package magmac.app.compile.error;

import magmac.api.Tuple2;
import magmac.api.result.Result;
import magmac.app.compile.error.error.CompileError;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public record InlineCompileResult<T>(Result<T, CompileError> toResult) implements CompileResult<T> {
    @Override
    public <R> CompileResult<R> mapValue(Function<T, R> mapper) {
        return CompileResults.fromResult(this.toResult.mapValue(mapper));
    }

    @Override
    public <R> R match(Function<T, R> whenOk, Function<CompileError, R> whenErr) {
        return this.toResult.match(whenOk, whenErr);
    }

    @Override
    public CompileResult<T> mapErr(Function<CompileError, CompileError> mapper) {
        return CompileResults.fromResult(this.toResult.mapErr(mapper));
    }

    @Override
    public <R> CompileResult<R> flatMapValue(Function<T, CompileResult<R>> mapper) {
        return CompileResults.fromResult(this.toResult.flatMapValue((T t) -> mapper.apply(t).toResult()));
    }

    @Override
    public <R> CompileResult<Tuple2<T, R>> and(Supplier<CompileResult<R>> supplier) {
        return CompileResults.fromResult(this.toResult.and(() -> supplier.get().toResult()));
    }

    @Override
    public CompileResult<T> merge(Supplier<CompileResult<T>> other, BiFunction<T, T, T> merger) {
        return this.and(other).mapValue((Tuple2<T, T> tuple) -> this.merge(merger, tuple));
    }

    private T merge(BiFunction<T, T, T> merger, Tuple2<T, T> tuple) {
        T left0 = tuple.left();
        T right0 = tuple.right();
        return merger.apply(left0, right0);
    }
}
