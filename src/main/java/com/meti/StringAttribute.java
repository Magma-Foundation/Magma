package com.meti;

public class StringAttribute implements Attribute {
    private final String value;

    public StringAttribute(String value) {
        this.value = value;
    }

    @Override
    public String computeString() {
        return value;
    }
}