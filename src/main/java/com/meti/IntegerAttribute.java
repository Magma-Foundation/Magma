package com.meti;

public record IntegerAttribute(int value) implements Attribute {
    @Override
    public int asInteger() {
        return value;
    }
}
