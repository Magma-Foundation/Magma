package com.meti.compile;

import com.meti.compile.AttributeException;
import com.meti.compile.Input;
import com.meti.compile.Node;

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

    default Stream<Node> streamNodes() throws AttributeException {
        throw new AttributeException("Not a list of nodes.");
    }

    enum Type {
        Value,
        Identity,
        Type,
        Children, Sign, Bits, Name
    }

    enum Group {
        Field, Node
    }
}
