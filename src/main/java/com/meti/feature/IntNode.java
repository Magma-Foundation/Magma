package com.meti.feature;

import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public record IntNode(int value) implements Node {

    @Override
    public Option<Integer> valueAsInt() {
        return Some.apply(value);
    }

    @Override
    public boolean is(Object key) {
        return key == Key.Id;
    }

    enum Key {
        Id
    }
}