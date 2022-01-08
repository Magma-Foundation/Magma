package com.meti;

public interface Node {
    default Node getValueAsNode() throws AttributeException {
        throw new AttributeException("No value is present of type Node.");
    }

    String getValueAsString() throws AttributeException;

    boolean is(Type type);

    default Node withValue(Node child) {
        return this;
    }

    enum Type {
        Content,
        Return
    }
}
