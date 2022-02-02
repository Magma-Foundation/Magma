package com.meti.app.compile.node.attribute;

import com.meti.api.collect.stream.Stream;
import com.meti.api.json.JSONException;
import com.meti.api.json.JSONNode;
import com.meti.app.compile.common.Field;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.Output;
import com.meti.app.source.Packaging;

public interface Attribute {
    default boolean asBoolean() throws AttributeException {
        throw new AttributeException("Not a boolean.");
    }

    default Input asInput() throws AttributeException {
        return failDefault("Not input, but rather '%s'. Had content of:\n-----\n%s\n-----\n");
    }

    private <T> T failDefault(String format) throws AttributeException {
        try {
            var message = format.formatted(getClass(), toJSON());
            throw new AttributeException(message);
        } catch (JSONException e) {
            throw new AttributeException(e);
        }
    }

    JSONNode toJSON() throws JSONException;

    default int asInteger() throws AttributeException {
        throw new AttributeException("Not an integer.");
    }

    default Node asNode() throws AttributeException {
        return failDefault("Not a node, but rather '%s'. Had content of:\n-----\n%s\n-----\n");
    }

    default Output asOutput() throws AttributeException {
        return failDefault("Not output, but rather '%s'. Had content of:\n-----\n%s\n-----\n");
    }

    default Packaging asPackaging() throws AttributeException {
        throw new AttributeException("Not a package.");
    }

    default Stream<Field.Flag> asStreamOfFlags() throws AttributeException {
        throw new UnsupportedOperationException("Not a list of flags.");
    }

    default Stream<Node> asStreamOfNodes() throws AttributeException {
        throw new AttributeException("Not a stream of nodes.");
    }

    default com.meti.app.compile.node.Type asType() throws AttributeException {
        return failDefault("Not a type, but rather '%s'. Had content of:\n-----\n%s\n-----\n");
    }

    enum Type {
        Value, Identity, Type, Children, Sign, Bits, Fields, Parameters, Flags, Caller, Arguments, Operator, Name
    }

    enum Group {
        Definition, Nodes, Definitions, Type, Node, Types,
    }
}
