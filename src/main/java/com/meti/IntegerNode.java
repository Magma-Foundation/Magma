package com.meti;

public record IntegerNode(int value) implements Node {
    @Override
    public Group group() {
        return Group.Integer;
    }

    @Override
    public String render() {
        return String.valueOf(value);
    }
}
