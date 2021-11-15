package com.meti.api.stream;

public class RangeStream implements Stream<Integer> {
    private final int terminus;
    private int counter;

    public RangeStream(int initial, int terminus) {
        this.terminus = terminus;
        this.counter = initial;
    }

    @Override
    public Integer head() throws StreamException {
        if (counter < terminus) return counter++;
        throw new EndOfStreamException("Counter has exceeded terminus.");
    }
}
