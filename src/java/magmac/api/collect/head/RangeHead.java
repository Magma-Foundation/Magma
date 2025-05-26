package magmac.api.collect.head;

import java.util.Optional;

public class RangeHead implements Head<Integer> {
    private final int length;
    private int counter;

    public RangeHead(int length) {
        this.length = length;
        this.counter = 0;
    }

    @Override
    public Optional<Integer> next() {
        if (this.counter < this.length) {
            int value = this.counter;
            this.counter++;
            return Optional.of(value);
        }
        else {
            return Optional.empty();
        }
    }
}
