package magmac.api.head;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;

public class RangeHead implements Head<Integer> {
    private final int length;
    private int counter;

    public RangeHead(int length) {
        this.length = length;
        this.counter = 0;
    }

    @Override
    public Option<Integer> next() {
        if (this.counter < this.length) {
            int value = this.counter;
            this.counter++;
            return new Some<>(value);
        }
        else {
            return new None<>();
        }
    }
}
