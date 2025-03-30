package magma.collect.stream.head;

import magma.option.None;
import magma.option.Option;
import magma.option.Some;

public class RangeHead implements Head<Integer> {
    private final int extent;
    private int counter = 0;

    public RangeHead(int extent) {
        this.extent = extent;
    }

    @Override
    public Option<Integer> next() {
        if (counter >= extent) return new None<>();

        int value = counter;
        counter++;
        return new Some<>(value);
    }
}
