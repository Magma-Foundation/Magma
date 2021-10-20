package com.meti;

public record NodeAttribute(Node value) implements Attribute {
    @Override
    public Node asNode() throws AttributeException {
        return value;
    }
}
