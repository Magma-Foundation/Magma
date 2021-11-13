package com.meti;

public class IntegerRenderer {
    private final IntegerNode node;

    public IntegerRenderer(IntegerNode node) {
        this.node = node;
    }

    String render() {
        return new Some<>(String.valueOf(node.getValue())).orElse("");
    }
}
