package com.meti.compile.attribute;

public record IntegerAttribute(int value) implements Attribute {

    @Override
    public int asInteger() {
        return value;
    }
}
