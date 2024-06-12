package magma.api;

import java.util.Optional;

public record Ok<T, E>(T value) implements Result<T, E> {
    @Override
    public Optional<T> findValue() {
        return Optional.of(value);
    }
}
