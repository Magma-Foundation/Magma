package com.meti;

public record InlineNode(String value) implements Node {
    @Override
    public String render() {
        return value;
    }
}
