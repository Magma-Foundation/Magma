package magma.list;

import magma.option.None;
import magma.option.Option;
import magma.option.Some;

public final class RangeHead implements Head<Integer> {
    private final int extent;
    private int counter = 0;

    public RangeHead(int extent) {
        this.extent = extent;
    }

    @Override
    public Option<Integer> next() {
        if (counter >= extent) return new None<>();
        int current = counter;
        counter++;
        return new Some<>(current);
    }
}
