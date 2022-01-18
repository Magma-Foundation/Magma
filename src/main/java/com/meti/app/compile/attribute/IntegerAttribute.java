package com.meti.app.compile.attribute;

public record IntegerAttribute(int value) implements Attribute {

    @Override
    public int asInteger() {
        return value;
    }
}
