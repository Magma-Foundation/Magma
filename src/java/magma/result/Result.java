package magma.result;

import java.util.Optional;

public interface Result<T, X> {
    Optional<T> findValue();

    Optional<X> findError();
}
