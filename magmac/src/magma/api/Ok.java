package magma.api;

import java.util.Optional;
import java.util.function.Function;

public record Ok<T, E>(T value) implements Result<T, E> {
    @Override
    public Optional<T> findValue() {
        return Optional.of(value);
    }

    @Override
    public Optional<E> findErr() {
        return Optional.empty();
    }

    @Override
    public <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return new Ok<>(mapper.apply(value));
    }

    @Override
    public boolean isOk() {
        return true;
    }
}
