package magmac.api.collect.head;

import magmac.api.collect.Head;

import java.util.Optional;

public class EmptyHead<T> implements Head<T> {
    @Override
    public Optional<T> next() {
        return Optional.empty();
    }
}
