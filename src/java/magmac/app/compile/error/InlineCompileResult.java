package magmac.app.compile.error;

import magmac.api.Tuple2;
import magmac.api.result.Result;
import magmac.app.compile.error.error.CompileError;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public final class InlineCompileResult<T> implements CompileResult<T> {
    private final Result<T, CompileError> result;

    private InlineCompileResult(Result<T, CompileError> result) {
        this.result = result;
    }

    public static <T> CompileResult<T> fromResult(Result<T, CompileError> result) {
        return new InlineCompileResult<T>(result);
    }

    @Override
    public <R> CompileResult<R> mapValue(Function<T, R> mapper) {
        return InlineCompileResult.fromResult(this.result.mapValue(mapper));
    }

    @Override
    public <R> R match(Function<T, R> whenOk, Function<CompileError, R> whenErr) {
        return this.result.match(whenOk, whenErr);
    }

    @Override
    public CompileResult<T> mapErr(Function<CompileError, CompileError> mapper) {
        return InlineCompileResult.fromResult(this.result.mapErr(mapper));
    }

    @Override
    public <R> CompileResult<R> flatMapValue(Function<T, CompileResult<R>> mapper) {
        return InlineCompileResult.fromResult(this.result.flatMapValue((T t) -> mapper.apply(t).result()));
    }

    @Override
    public <R> CompileResult<Tuple2<T, R>> and(Supplier<CompileResult<R>> supplier) {
        return InlineCompileResult.fromResult(this.result.and(() -> supplier.get().result()));
    }

    @Override
    public Result<T, CompileError> result() {
        return this.result;
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
