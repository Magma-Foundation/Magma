package com.meti.iterate;

import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;

public record Index(int value, int length) {
    public static Index startOf(Range value) {
        return new Index(value.start, value.length);
    }

    public static Option<Index> endOf(Range range) {
        if (range.end >= range.length) {
            return None.apply();
        } else {
            return Some.apply(new Index(range.end, range.length));
        }
    }

    public Option<Range> to(Index other) {
        if (value <= other.value) {
            return Some.apply(new Range(value, other.value, length));
        } else {
            return None.apply();
        }
    }

    public int unwrap() {
        return value;
    }

    public Option<Index> nextExclusive() {
        return nextExclusive(1);
    }

    public Option<Index> nextExclusive(int sliceLength) {
        var next = value + sliceLength;
        if (next < length) {
            return new Some<>(new Index(next, length));
        } else {
            return None.apply();
        }
    }

    public boolean isStart() {
        return this.value == 0;
    }

    public boolean isEnd() {
        return this.value == length - 1;
    }
}