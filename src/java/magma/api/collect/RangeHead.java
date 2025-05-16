package magma.api.collect;

import magma.api.option.Option;
import magma.app.Main;

public final class RangeHead implements Head<Integer> {
    private final int length;
    private int counter;

    public RangeHead(int length) {
        this.length = length;
        this.counter = 0;
    }

    @Override
    public Option<Integer> next() {
        if (this.counter >= this.length) {
            return new Main.None<Integer>();
        }

        var value = this.counter;
        this.counter++;
        return new Main.Some<Integer>(value);
    }
}
