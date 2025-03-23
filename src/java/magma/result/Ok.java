package magma.result;

import java.util.Optional;

public record Ok<T, X>(T value) implements Result<T, X> {
    @Override
    public Optional<T> findValue() {
        return Optional.of(value);
    }

    @Override
    public Optional<X> findError() {
        return Optional.empty();
    }
}
