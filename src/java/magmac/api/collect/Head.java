package magmac.api.collect;

import java.util.Optional;

public interface Head<T> {
    Optional<T> next();
}
