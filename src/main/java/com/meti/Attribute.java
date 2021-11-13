package com.meti;

public interface Attribute {
    enum Type {
        Value
    }

    enum Group {
        Node
    }

    default int asInt() throws AttributeException {
        throw new AttributeException("Not an int.");
    }

    default Node asNode() throws AttributeException {
        throw new AttributeException("Not a node.");
    }

    default String asString() throws AttributeException {
        throw new AttributeException("Not a string.");
    }
}
