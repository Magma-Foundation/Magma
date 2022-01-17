package com.meti.compile.attribute;

public record BooleanAttribute(boolean value) implements Attribute {
    @Override
    public boolean asBoolean() {
        return value;
    }
}
