package magmac.app.compile.error;

import magmac.api.Tuple2;
import magmac.api.result.Result;
import magmac.app.compile.error.error.CompileError;

import java.util.function.Function;
import java.util.function.Supplier;

public record CompileResult<T>(Result<T, CompileError> result) {
    public <R> CompileResult<R> mapValue(Function<T, R> mapper) {
        return new CompileResult<>(this.result.mapValue(mapper));
    }

    public <R> R match(Function<T, R> whenOk, Function<CompileError, R> whenErr) {
        return result.match(whenOk, whenErr);
    }

    public CompileResult<T> mapErr(Function<CompileError, CompileError> mapper) {
        return new CompileResult<>(this.result.mapErr(mapper));
    }

    public <R> CompileResult<R> flatMapValue(Function<T, CompileResult<R>> mapper) {
        return new CompileResult<>(this.result.flatMapValue(t -> mapper.apply(t).result));
    }

    public <R> CompileResult<Tuple2<T, R>> and(Supplier<CompileResult<R>> supplier) {
        return new CompileResult<>(this.result.and(() -> supplier.get().result));
    }
}
