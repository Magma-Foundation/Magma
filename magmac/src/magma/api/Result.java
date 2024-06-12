package magma.api;

import java.util.Optional;

public interface Result<T, E> {
    Optional<T> findValue();
}
