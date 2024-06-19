package magma.api;

import java.util.Optional;

public interface Head<T> {
    Optional<T> head();
}
