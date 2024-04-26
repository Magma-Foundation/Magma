package com.meti.collect;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record Index(int value, int length) {
    public Option<Index> next() {
        var next = value + 1;
        if (next > length) {
            return new None<>();
        } else {
            return new Some<>(new Index(next, length));
        }
    }

    public boolean isStart() {
        return this.value == 0;
    }

    public boolean isEnd() {
        return this.value == length;
    }
}
