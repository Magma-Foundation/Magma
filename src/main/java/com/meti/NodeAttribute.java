package com.meti;

public class NodeAttribute implements Attribute {
    private final Node node;

    public NodeAttribute(Node node) {
        this.node = node;
    }

    @Override
    public Node asNode() {
        return node;
    }
}
