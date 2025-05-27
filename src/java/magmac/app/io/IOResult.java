package magmac.app.io;

import magmac.api.Tuple2;
import magmac.api.result.Result;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Supplier;

public record IOResult<T>(Result<T, IOException> result) {
    public <R> IOResult<Tuple2<T, R>> and(Supplier<IOResult<R>> supplier) {
        return new IOResult<>(this.result.and(() -> supplier.get().result));
    }

    public <R> IOResult<R> mapValue(Function<T, R> mapper) {
        return new IOResult<>(this.result.mapValue(mapper));
    }

    public <R> IOResult<R> flatMapValue(Function<T, IOResult<R>> mapper) {
        return new IOResult<>(this.result.flatMapValue(value -> mapper.apply(value).result));
    }

    public <R> Result<T, R> mapErr(Function<IOException, R> mapper) {
        return this.result.mapErr(mapper);
    }
}