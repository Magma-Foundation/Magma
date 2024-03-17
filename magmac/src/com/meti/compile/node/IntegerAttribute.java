package com.meti.compile.node;

import com.meti.collect.option.Option;
import com.meti.collect.option.Some;

public class IntegerAttribute implements Attribute {
    private final int value;

    public IntegerAttribute(int value) {
        this.value = value;
    }

    @Override
    public Option<Integer> asInteger() {
        return Some.Some(value);
    }
}
