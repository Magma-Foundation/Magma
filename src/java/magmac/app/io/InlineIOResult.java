package magmac.app.io;

import magmac.api.result.Result;

import java.io.IOException;
import java.util.function.Function;

public record InlineIOResult<T>(Result<T, IOException> result) implements IOResult<T> {
    @Override
    public <R> IOResult<R> mapValue(Function<T, R> mapper) {
        return new InlineIOResult<>(this.result.mapValue(mapper));
    }

    @Override
    public <R> IOResult<R> flatMapValue(Function<T, IOResult<R>> mapper) {
        return new InlineIOResult<>(this.result.flatMapValue(value -> mapper.apply(value).result()));
    }

    @Override
    public <R> Result<T, R> mapErr(Function<IOException, R> mapper) {
        return this.result.mapErr(mapper);
    }
}