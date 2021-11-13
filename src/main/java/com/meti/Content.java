package com.meti;

public record Content(String value) implements Node {
    @Override
    public Attribute apply() {
        return new StringAttribute(value);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Content;
    }
}
