package com.meti;

public record Return(Node value) implements Node {
    @Override
    public Attribute apply() {
        return new NodeAttribute(value);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Return;
    }

    @Override
    public Node with(Node value) {
        return new Return(value);
    }
}
