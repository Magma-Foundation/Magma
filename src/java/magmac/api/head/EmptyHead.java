package magmac.api.head;

import java.util.Optional;

public class EmptyHead<T> implements Head<T> {
    @Override
    public Optional<T> next() {
        return Optional.empty();
    }
}
