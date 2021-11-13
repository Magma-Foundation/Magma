package com.meti.attribute;

import com.meti.node.Node;
import com.meti.stream.Stream;

public interface Attribute {
    default boolean asBoolean() throws AttributeException {
        throw new AttributeException("Not a boolean.");
    }

    default int asInt() throws AttributeException {
        throw new AttributeException("Not an int.");
    }

    default Node asNode() throws AttributeException {
        throw new AttributeException("Not a node.");
    }

    default Stream<? extends Node> asNodeStream() throws AttributeException {
        throw new AttributeException("Not a list of nodes.");
    }

    default String asString() throws AttributeException {
        throw new AttributeException("Not a string.");
    }

    enum Type {
        Bits,
        Children,
        Identity,
        Name,
        PreEquality,
        Signed,
        Value
    }

    enum Group {
        Field,
        Node,
        Nodes,
    }
}
