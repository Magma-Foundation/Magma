package com.meti.compile.attribute;

import com.meti.compile.node.Input;
import com.meti.compile.node.Node;

import java.util.stream.Stream;

public interface Attribute {
    default boolean asBoolean() throws AttributeException {
        throw new AttributeException("Not a boolean.");
    }

    default Input asInput() throws AttributeException {
        throw new AttributeException("Not input.");
    }

    default int asInteger() throws AttributeException {
        throw new AttributeException("Not an integer.");
    }

    default Node asNode() throws AttributeException {
        throw new AttributeException("Not a node.");
    }

    default Stream<Node> asStreamOfNodes() throws AttributeException {
        throw new AttributeException("Not a list of nodes.");
    }

    enum Type {
        Value,
        Identity,
        Type,
        Children, Sign, Bits, Name
    }

    enum Group {
        Field, Nodes, Node
    }
}
