package com.meti;

public record IntegerNode(int value) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) {
        return new IntAttribute(value);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Int;
    }
}
