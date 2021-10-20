package com.meti;

public record Content(String value) implements Node {
    @Override
    public String value() {
        return value;
    }

    @Override
    public boolean is(Type type) {
        throw new UnsupportedOperationException();
    }
}
