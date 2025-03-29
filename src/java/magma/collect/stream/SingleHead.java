package magma.collect.stream;

import magma.option.None;
import magma.option.Option;
import magma.option.Some;

public class SingleHead<T> implements Head<T> {
    private final T value;
    private boolean retrieved = false;

    public SingleHead(T value) {
        this.value = value;
    }

    @Override
    public Option<T> next() {
        if (retrieved) return new None<>();

        retrieved = true;
        return new Some<>(value);
    }
}
