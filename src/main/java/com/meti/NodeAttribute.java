package com.meti;

public class NodeAttribute implements Attribute {
    private final Node value;

    public NodeAttribute(Node value) {
        this.value = value;
    }

    @Override
    public Node asNode() {
        return value;
    }
}
