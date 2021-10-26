package com.meti.app.compile.node.attribute;

public record StringAttribute(String value) implements Attribute {
    @Override
    public String asString() {
        return value;
    }
}
