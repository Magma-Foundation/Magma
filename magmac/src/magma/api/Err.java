package magma.api;

import java.util.Optional;
import java.util.function.Function;

public record Err<T, E>(E value) implements Result<T, E> {
    @Override
    public Optional<T> findValue() {
        return Optional.empty();
    }

    @Override
    public Optional<E> findErr() {
        return Optional.of(value);
    }

    @Override
    public <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper) {
        return new Err<>(value);
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return new Err<>(value);
    }
}
