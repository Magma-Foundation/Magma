package com.meti.feature.integer;

import com.meti.feature.attribute.Attribute;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public class IntAttribute implements Attribute {
    private final int value;

    public IntAttribute(int value) {
        this.value = value;
    }

    @Override
    public Option<Integer> asInteger() {
        return Some.apply(value);
    }
}
