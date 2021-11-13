package com.meti;

public class IntegerRenderer {
    private final IntegerNode node;

    public IntegerRenderer(IntegerNode node) {
        this.node = node;
    }

    String render() throws CompileException {
        return new Some<>(String.valueOf(node.apply().asInt())).orElse("");
    }
}
