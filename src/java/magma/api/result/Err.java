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
}
