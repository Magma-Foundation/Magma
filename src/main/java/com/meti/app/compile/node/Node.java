package com.meti.app.compile.node;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.api.core.F1;
import com.meti.api.json.JSONException;
import com.meti.api.json.JSONNode;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;

public interface Node extends JSONable {
    default Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return Streams.empty();
    }

    boolean is(Role role);

    default <E extends Exception> Node mapAsNode(Attribute.Type type, F1<Node, Node, E> mapper) throws E, AttributeException {
        var input = apply(type).asNode();
        var output = mapper.apply(input);
        return with(type, new NodeAttribute(output));
    }

    default Attribute apply(Attribute.Type type) throws AttributeException {
        var format = "Node instance of '%s' has no attributes.";
        var message = format.formatted(getClass());
        throw new AttributeException(message);
    }

    default Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        throw new AttributeException("Node does not have attribute to replace of: " + type);
    }

    @Override
    default JSONNode toJSON() throws JSONException {
        var format = "Instances of '%s' cannot be converted to JSON yet.";
        var message = format.formatted(getClass());
        throw new JSONException(message);
    }

    enum Role {
        Abstraction, Binary, Block, Boolean,
        Cache, Declaration, Else, Empty,
        Extern, Function, If, Implementation,
        Implicit, Import, Initialization, Input,
        Integer, Invocation, Line, Output,
        Primitive, Reference, Return, String,
        Structure, Unary, Union, Variable
    }
}
