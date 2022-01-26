package com.meti.app.compile.common;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.json.ArrayNode;
import com.meti.api.json.EmptyNode;
import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;

import java.util.Objects;
import java.util.stream.Stream;

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
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Node ? Stream.of(Attribute.Type.Value) : super.apply(group);
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
                    .foldRight(new ArrayNode.Builder(), ArrayNode.Builder::add)
                    .builder();

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
        return "{" + "\n\t\"identity\":" + identity + ",\n\t\"parameters\":" + parameters + ",\n\t\"body\":" + body + '}';
    }
}
