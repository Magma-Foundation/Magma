package com.meti;

public interface Attribute {
    default int asInt() throws AttributeException {
        throw new AttributeException("Not an int.");
    }

    default Node asNode() throws AttributeException {
        throw new AttributeException("Not a node.");
    }

    default Stream<Node> asNodeStream() throws AttributeException {
        throw new AttributeException("Not a list of nodes.");
    }

    default String asString() throws AttributeException {
        throw new AttributeException("Not a string.");
    }

    enum Type {
        Children, Value
    }

    enum Group {
        Nodes, Node
    }
}
