package magma.result;

import java.util.Optional;

public record Err<T, X>(X error) implements Result<T, X> {
    @Override
    public Optional<T> findValue() {
        return Optional.empty();
    }

    @Override
    public Optional<X> findError() {
        return Optional.of(error);
    }
}
