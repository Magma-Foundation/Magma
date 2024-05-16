package com.meti.api;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public record Index(int value) {
    public Option<Index> next() {
        var nextValue = value() + 1;
        if (nextValue <= length()) {
            return new Some<>(new Index(nextValue));
        } else {
            return new None<>();
        }
    }

    public int length() {
        return 0;
    }
}
