package magmac.api.head;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;

public class SingleHead<T> implements Head<T> {
    private final T element;
    private boolean retrieved;

    public SingleHead(T element) {
        this.element = element;
        this.retrieved = false;
    }

    @Override
    public Option<T> next() {
        if (this.retrieved) {
            return new None<>();
        }

        this.retrieved = true;
        return new Some<>(this.element);
    }
}
