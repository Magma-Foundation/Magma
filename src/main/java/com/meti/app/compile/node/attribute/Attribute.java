package com.meti.app.compile.node.attribute;

import com.meti.api.json.JSONNode;
import com.meti.app.compile.common.EmptyField;
import com.meti.app.compile.common.Field;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.Output;
import com.meti.app.source.Packaging;

import java.util.stream.Stream;

public interface Attribute {
    default boolean asBoolean() throws AttributeException {
        throw new AttributeException("Not a boolean.");
    }

    default Input asInput() throws AttributeException {
        throw new AttributeException("Not output.");
    }

    default int asInteger() throws AttributeException {
        throw new AttributeException("Not an integer.");
    }

    default Node asNode() throws AttributeException {
        throw new AttributeException("Not a node.");
    }

    default Output asOutput() throws AttributeException {
        var format = "Not output, but rather '%s'. Had content of:\n-----\n%s\n-----\n";
        var message = format.formatted(getClass(), toJSON());
        throw new AttributeException(message);
    }

    JSONNode toJSON();

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

    enum Type {
        Value, Identity, Type, Children, Sign, Bits, Fields, Parameters, Flags, Caller, Arguments, Operator, Name
    }

    enum Group {
        Definition, Nodes, Definitions, Type, Node, Types,
    }
}
