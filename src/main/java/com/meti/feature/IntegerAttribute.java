package com.meti.feature;

import com.meti.Attribute;

public record IntegerAttribute(int value) implements Attribute {
    @Override
    public int asInteger() {
        return value;
    }
}
