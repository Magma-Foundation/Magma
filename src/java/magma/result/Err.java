package magma.result;

import magma.option.None;
import magma.option.Option;
import magma.option.Some;

public record Err<T, X>(X error) implements Result<T, X> {
    @Override
    public Option<T> findValue() {
        return new None<>();
    }

    @Override
    public Option<X> findError() {
        return new Some<>(error);
    }
}
