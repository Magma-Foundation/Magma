package com.meti;

public record Index(int value, int length) {
    public Option<Index> next() {
        return nextBy(1);
    }

    public Option<Index> nextBy(int count) {
        var nextLength = value + count;
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

    public boolean isStart() {
        return value == 0;
    }

    public boolean isEnd() {
        return value == length - 1;
    }
}
