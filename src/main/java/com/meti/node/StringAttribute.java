package com.meti.node;

public class StringAttribute implements Attribute {
    private final String value;

    public StringAttribute(String value) {
        this.value = value;
    }

    @Override
    public String asString() {
        return value;
    }
}
