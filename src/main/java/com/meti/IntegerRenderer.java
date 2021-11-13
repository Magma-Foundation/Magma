package com.meti;

public class IntegerRenderer {
    private final Node node;

    public IntegerRenderer(Node node) {
        this.node = node;
    }

    String render() throws CompileException {
        return new Some<>(String.valueOf(node.apply().asInt())).orElse("");
    }
}
