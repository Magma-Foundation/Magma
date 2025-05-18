package magma.api.collect.head;

import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

public final class SingleHead<T> implements Head<T> {
    private final T element;
    private boolean retrieved;

    public SingleHead(T element) {
        this.element = element;
        this.retrieved = false;
    }

    @Override
    public Option<T> next() {
        if (this.retrieved) {
            return new None<T>();
        }

        this.retrieved = true;
        return new Some<T>(this.element);
    }
}
