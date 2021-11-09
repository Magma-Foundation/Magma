package com.meti.app.compile.node.attribute;

import com.meti.api.Stream;
import com.meti.app.compile.node.Input;
import com.meti.app.compile.node.Node;

public interface Attribute {
    default Input asInput() throws AttributeException {
        throw new AttributeException("Not input.");
    }

    default int asInteger() throws AttributeException {
        throw new AttributeException("Not an integer.");
    }

    default Node asNode() throws AttributeException {
        throw new AttributeException("Not a node.");
    }

    default Stream<? extends Node> asNodeStream() throws AttributeException {
        throw new AttributeException("Not a sequence of nodes.");
    }

    default String asString() throws AttributeException {
        throw new AttributeException("Not a string.");
    }

    enum Group {
        NodeList, Node
    }

    enum Type {
        Children,
        Name,
        Identity,
        Value,
        Type
    }
}
