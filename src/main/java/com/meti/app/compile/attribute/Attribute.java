package com.meti.app.compile.attribute;

import com.meti.app.compile.common.EmptyField;
import com.meti.app.compile.common.Field;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Text;
import com.meti.app.source.Packaging;

import java.util.stream.Stream;

public interface Attribute {
    default boolean asBoolean() throws AttributeException {
        throw new AttributeException("Not a boolean.");
    }

    default int asInteger() throws AttributeException {
        throw new AttributeException("Not an integer.");
    }

    default Node asNode() throws AttributeException {
        throw new AttributeException("Not a oldNode.");
    }

    default Packaging asPackaging() throws AttributeException {
        throw new AttributeException("Not a package.");
    }

    @Deprecated
    default Stream<EmptyField.Flag> asStreamOfFlags() {
        throw new UnsupportedOperationException();
    }

    default com.meti.api.collect.stream.Stream<Field.Flag> asStreamOfFlags1() throws AttributeException {
        throw new UnsupportedOperationException("Not a list of flags.");
    }

    @Deprecated
    default Stream<Node> asStreamOfNodes() throws AttributeException {
        throw new AttributeException("Not a list of nodes.");
    }

    default com.meti.api.collect.stream.Stream<Node> asStreamOfNodes1() throws AttributeException {
        throw new AttributeException("Not a stream of nodes.");
    }

    default Text asText() throws AttributeException {
        throw new AttributeException("Not input.");
    }

    enum Type {
        Value,
        Identity,
        Type,
        Children, Sign, Bits, Fields, Parameters, Flags, Caller, Arguments, Operator, Name
    }

    enum Group {
        Definition, Nodes, Declarations, Type, Node, Types,
    }
}
