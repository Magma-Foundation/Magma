package magmac.api.collect.head;

import magmac.api.collect.Head;

import java.util.Optional;

public class SingleHead<T> implements Head<T> {
    private final T element;
    private boolean retrieved;

    public SingleHead(T element) {
        this.element = element;
        this.retrieved = false;
    }

    @Override
    public Optional<T> next() {
        if (this.retrieved) {
            return Optional.empty();
        }

        this.retrieved = true;
        return Optional.of(this.element);
    }
}
