package magma.api.stream;

import java.util.Optional;

public interface Head<T> {
    Optional<T> head();
}
