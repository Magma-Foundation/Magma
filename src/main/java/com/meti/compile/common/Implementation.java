package com.meti.compile.common;

import com.meti.collect.JavaList;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.node.Node;

import java.util.Set;
import java.util.stream.Stream;

public final class Implementation extends Function {
    private final Node body;

    public Implementation(Node identity, Set<Node> parameters, Node body) {
        super(identity, parameters);
        this.body = body;
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Node
                ? Stream.of(Attribute.Type.Value)
                : super.apply(group);
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return type == Attribute.Type.Value
                ? new NodeAttribute(body)
                : super.apply(type);
    }

    @Override
    protected Node complete(Node node, JavaList<Node> parameters) {
        return new Implementation(node, parameters, body);
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return type == Attribute.Type.Value
                ? new Implementation(identity, parameters, attribute.asNode())
                : super.with(type, attribute);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Implementation;
    }
}
