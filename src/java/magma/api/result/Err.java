package magma.api.result;

import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

import java.util.function.Function;

public record Err<T, X>(X error) implements Result<T, X> {
    @Override
    public Option<X> findError() {
        return new Some<X>(this.error);
    }

    @Override
    public Option<T> findValue() {
        return new None<T>();
    }

    @Override
    public <R> R match(final Function<T, R> whenOk, final Function<X, R> whenErr) {
        return whenErr.apply(this.error);
    }

    @Override
    public <R> Result<R, X> flatMapValue(final Function<T, Result<R, X>> mapper) {
        return new Err<>(this.error);
    }

    @Override
    public <R> Result<R, X> mapValue(final Function<T, R> mapper) {
        return new Err<>(this.error);
    }
}
