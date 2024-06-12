package magma.api;

import java.util.Optional;

public record Err<T, E>(E value) implements Result<T, E> {
    @Override
    public Optional<T> findValue() {
        return Optional.empty();
    }
}
