package com.meti.compile;

public class IntegerAttribute implements Attribute {
    private final int value;

    public IntegerAttribute(int value) {
        this.value = value;
    }

    @Override
    public int asInteger() throws AttributeException {
        return value;
    }
}
