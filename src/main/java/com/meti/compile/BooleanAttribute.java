package com.meti.compile;

public class BooleanAttribute implements Attribute {
    private final boolean value;

    public BooleanAttribute(boolean value) {
        this.value = value;
    }

    @Override
    public boolean asBoolean() throws AttributeException {
        return value;
    }
}
