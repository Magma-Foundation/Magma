package com.meti;

public record Index(int value, int length) {
    public Option<Index> next() {
        var nextLength = value + 1;
        if (nextLength > length) {
            return None.apply();
        } else {
            return Some.apply(new Index(nextLength, length));
        }
    }

    public Option<Range> to(Index extent) {
        if (value <= extent.value) {
            return Some.apply(new Range(value, extent.value));
        } else {
            return None.apply();
        }
    }
}
