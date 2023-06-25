package com.meti.safe;

import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public class Index {
    private final int value;
    private final int length;

    Index(int value, int length) {
        this.value = value;
        this.length = length;
    }

    public int length() {
        return length;
    }

    public Option<Range> to(Index other) {
        if ((other.value - value) >= 0) {
            return new Some<>(new Range(value, other.value));
        } else {
            return new None<>();
        }
    }

    public Option<Index> next() {
        if (this.value + 1 < length) {
            return new Some<>(new Index(this.value + 1, length));
        } else {
            return new None<>();
        }
    }

    public int value() {
        return value;
    }
}
