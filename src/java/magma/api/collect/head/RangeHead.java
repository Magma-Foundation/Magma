package magma.api.collect.head;

import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

public final class RangeHead implements Head<Integer> {
    private final int length;
    private int counter;

    public RangeHead(final int length) {
        this.length = length;
        this.counter = 0;
    }

    @Override
    public Option<Integer> next() {
        if (this.counter >= this.length) {
            return new None<Integer>();
        }

        final var value = this.counter;
        this.counter++;
        return new Some<Integer>(value);
    }
}
