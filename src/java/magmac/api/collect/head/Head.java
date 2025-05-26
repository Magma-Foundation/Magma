package magmac.api.collect.head;

import java.util.Optional;

public interface Head<T> {
    Optional<T> next();
}
