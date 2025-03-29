package magma.result;

import magma.option.None;
import magma.option.Option;
import magma.option.Some;

import java.util.function.Function;

public record Err<T, X>(X error) implements Result<T, X> {
    @Override
    public Option<T> findValue() {
        return new None<>();
    }

    @Override
    public Option<X> findError() {
        return new Some<>(error);
    }

    @Override
    public <R> Result<R, X> mapValue(Function<T, R> mapper) {
        return new Err<>(error);
    }

    @Override
    public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
        return new Err<>(error);
    }
}
