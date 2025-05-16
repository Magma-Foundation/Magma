package magma.api.result;

import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

import java.util.function.Function;

public record Ok<T, X>(T value) implements Result<T, X> {
    @Override
    public Option<X> findError() {
        return new None<X>();
    }

    @Override
    public Option<T> findValue() {
        return new Some<T>(this.value);
    }

    @Override
    public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
        return whenOk.apply(this.value);
    }
}
