package com.meti;

public record BooleanAttribute(boolean value) implements Attribute {
    @Override
    public boolean asBoolean() {
        return value;
    }
}
