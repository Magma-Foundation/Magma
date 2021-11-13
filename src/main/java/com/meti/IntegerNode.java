package com.meti;

public record IntegerNode(int value) {
    public Attribute apply() {
        return new IntAttribute(value);
    }
}
