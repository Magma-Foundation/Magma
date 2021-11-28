package com.meti.app.attribute;

public record IntAttribute(int value) implements Attribute {
    @Override
    public int asInt() {
        return value;
    }
}
