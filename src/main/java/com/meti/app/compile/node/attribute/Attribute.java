package com.meti.app.compile.node.attribute;

import com.meti.api.collect.stream.Stream;
import com.meti.api.json.JSONException;
import com.meti.api.json.JSONNode;
import com.meti.app.compile.common.Definition;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Type;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.Output;
import com.meti.app.source.Packaging;

public interface Attribute {
    default boolean asBoolean() throws AttributeException {
        return fail("a boolean");
    }

    default Input asInput() throws AttributeException {
        return fail("input");
    }

    JSONNode toJSON() throws JSONException;

    default int asInteger() throws AttributeException {
        return fail("an integer");
    }

    default Node asNode() throws AttributeException {
        return fail("a node");
    }

    private <T> T fail(String group) throws AttributeException {
        var format = "Not %s, but rather '%s'. Had content of:\n-----\n%s\n-----\n";
        var message = format.formatted(group, getClass(), toString());
        throw new AttributeException(message);
    }

    default Output asOutput() throws AttributeException {
        return fail("output");
    }

    default Packaging asPackaging() throws AttributeException {
        return fail("packaging");
    }

    default Stream<Definition.Flag> asStreamOfFlags() throws AttributeException {
        return fail("a sequence of flags");
    }

    default Stream<Node> asStreamOfNodes() throws AttributeException {
        return fail("a sequence of nodes");
    }

    default Type asType() throws AttributeException {
        return fail("a type");
    }

    enum Category {
        Arguments, Bits, Caller, Children,
        Fields, Flags, Identity, Name,
        Operator, Parameters, Sign, Type,
        Value
    }

    enum Group {
        Definition, Nodes, Definitions, Type, Node, Types,
    }
}
