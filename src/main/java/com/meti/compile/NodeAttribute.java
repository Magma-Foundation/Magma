package com.meti.compile;

public record NodeAttribute(Node node) implements Attribute {
    @Override
    public Node asNode() {
        return node;
    }
}
