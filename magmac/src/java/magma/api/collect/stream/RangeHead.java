package magma.api.collect.stream;

import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

public final class RangeHead implements Head<Integer> {
    private final int extent;
    private int counter;

    public RangeHead(int initial, int extent) {
        this.counter = initial;
        this.extent = extent;
    }

    @Override
    public Option<Integer> head() {
        if (counter >= extent) return new None<>();

        var value = counter;
        counter++;
        return new Some<>(value);
    }
}
