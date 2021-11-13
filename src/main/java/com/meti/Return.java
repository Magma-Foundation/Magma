package com.meti;

public record Return(Node value) implements Node {
    @Override
    public Attribute apply() {
        return new NodeAttribute(value);
    }
}
