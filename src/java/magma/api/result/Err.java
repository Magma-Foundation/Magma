package magma.api.result;

import magma.api.option.Option;
import magma.api.option.Some;

import java.util.function.Function;

public record Err<T, X>(X error) implements Result<T, X> {
    @Override
    public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
        return whenErr.apply(this.error);
    }

    @Override
    public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
        return new Err<R, X>(this.error);
    }

    @Override
    public Option<X> findError() {
        return new Some<X>(this.error);
    }

    @Override
    public <R> Result<R, X> mapValue(Function<T, R> mapper) {
        return new Err<R, X>(this.error);
    }
}
