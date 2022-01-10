package com.meti.compile.attribute;

import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.source.Packaging;

import java.util.stream.Stream;

public interface Attribute {
    default boolean asBoolean() throws AttributeException {
        throw new AttributeException("Not a boolean.");
    }

    default int asInteger() throws AttributeException {
        throw new AttributeException("Not an integer.");
    }

    default Node asNode() throws AttributeException {
        throw new AttributeException("Not a node.");
    }

    default Packaging asPackaging() throws AttributeException {
        throw new AttributeException("Not a package.");
    }

    default Stream<Node> asStreamOfNodes() throws AttributeException {
        throw new AttributeException("Not a list of nodes.");
    }

    default Text asText() throws AttributeException {
        throw new AttributeException("Not input.");
    }

    enum Type {
        Value,
        Identity,
        Type,
        Children, Sign, Bits, Fields, Name
    }

    enum Group {
        Declaration, Nodes, Declarations, Node
    }
}
