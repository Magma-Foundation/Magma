package com.meti.collect;

import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;

public record Index(int value, int length) {
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
            return new None<>();
        }
    }
}