package com.meti;

public record InlineNode(String value) implements Node {
    @Override
    public Group group() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String render() {
        return value;
    }
}
