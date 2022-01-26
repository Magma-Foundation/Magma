package com.meti.app.compile.common;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.json.*;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;

import java.util.Objects;

public final class Implementation extends Function {
    private final Node body;

    public Implementation(Node identity, Node body, Node... parameters) {
        this(identity, body, List.apply(parameters));
    }

    public Implementation(Node identity, Node body, List<Node> parameters) {
        super(identity, parameters);
        this.body = body;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return type == Attribute.Type.Value ? new NodeAttribute(body) : super.apply(type);
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return type == Attribute.Type.Value ? new Implementation(identity, attribute.asNode(), parameters) : super.with(type, attribute);
    }

    @Override
    protected Node complete(Node node, List<Node> parameters) {
        return new Implementation(node, body, parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Implementation that = (Implementation) o;
        return super.equals(o) && Objects.equals(body, that.body);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Implementation;
    }

    @Override
    public JSONNode toJSON() {
        try {
            var jsonParameters = parameters.stream()
                    .map(Node::toJSON)
                    .foldRight(new ArrayNode.Builder(), ArrayNode.Builder::addObject)
                    .build();

            return new ObjectNode()
                    .add("identity", identity.toJSON())
                    .add("parameters", jsonParameters)
                    .add("body", body.toJSON());
        } catch (StreamException e) {
            return new EmptyNode();
        }
    }

    @Override
    public String toString() {
        return new JSONFormatter(toJSON()).toString();
    }
}
