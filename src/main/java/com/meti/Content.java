package com.meti;

public record Content(String value) implements Node {
    @Override
    public String getValueAsString() {
        return value;
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Content;
    }
}
