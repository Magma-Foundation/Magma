package com.meti;

public record Function(Node identity, Node body) implements Node {
    @Override
    public boolean is(Type type) {
        return type == Type.Function;
    }
}