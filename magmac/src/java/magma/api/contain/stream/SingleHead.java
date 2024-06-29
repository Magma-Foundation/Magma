package magma.api.contain.stream;

import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

public final class SingleHead<T> implements Head<T> {
    private final T value;
    private boolean retrieved = false;

    public SingleHead(T value) {
        this.value = value;
    }

    @Override
    public Option<T> head() {
        if (retrieved) return None.None();
        else {
            retrieved = true;
            return new Some<>(value);
        }
    }
}
