package com.meti.app.compile.node;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.core.F1;
import com.meti.api.json.JSONException;
import com.meti.api.json.JSONNode;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.NodesAttribute1;

public interface Node extends JSONable {
    default Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return Streams.empty();
    }

    boolean is(Type type);

    default <E extends Exception> Node mapAsNode(Attribute.Type type, F1<Node, Node, E> mapper) throws E, AttributeException {
        var input = apply(type).asNode();
        var output = mapper.apply(input);
        return with(type, new NodeAttribute(output));
    }

    default Attribute apply(Attribute.Type type) throws AttributeException {
        throw new AttributeException("Node had no attributes.");
    }

    default Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        throw new AttributeException("Node does not have attribute to replace of: " + type);
    }

    default <E extends Exception> Node mapAsNodeStream(Attribute.Type type, F1<com.meti.api.collect.stream.Stream<Node>, com.meti.api.collect.stream.Stream<? extends Node>, E> mapper) throws E, AttributeException {
        try {
            var input = apply(type).asStreamOfNodes1();
            var output = mapper.apply(input).foldRight(new NodesAttribute1.Builder(), NodesAttribute1.Builder::add).complete();
            return with(type, output);
        } catch (StreamException e) {
            throw new AttributeException(e);
        }
    }

    @Override
    default JSONNode toJSON() throws JSONException {
        var format = "Instances of '%s' cannot be converted to JSON yet.";
        var message = format.formatted(getClass());
        throw new JSONException(message);
    }

    enum Type {
        Input, Block, Implementation, Declaration, Integer, Structure, Primitive, Import, Extern, Variable, Boolean, Abstraction, Unary, Empty, If, String, Invocation, Line, Implicit, Reference, Initialization, Function, Else, Binary, Cache, Output, Return
    }
}
