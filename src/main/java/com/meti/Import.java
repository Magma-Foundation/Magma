package com.meti;

public record Import(String name) implements Node {
    @Override
    public boolean is(Type type) {
        throw new UnsupportedOperationException();
    }
}