package com.meti;

public record IntegerNode(int value) implements Node {
    @Override
    public Attribute apply() {
        return new IntAttribute(value);
    }
}
