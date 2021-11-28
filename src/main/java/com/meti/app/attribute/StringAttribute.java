package com.meti.app.attribute;

public record StringAttribute(String value) implements Attribute {
    @Override
    public String asString() {
        return value;
    }
}
