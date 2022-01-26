package com.meti.app.compile.common;

import com.meti.api.collect.java.List;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Line(Node node) implements Node {
    private Stream<Attribute.Type> apply2(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Node ? Stream.of(Attribute.Type.Value) : Stream.empty();
    }

    @Override
    public com.meti.api.collect.stream.Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return List.createList(apply2(group).collect(Collectors.toList())).stream();
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return type == Attribute.Type.Value ? new Line(attribute.asNode()) : this;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new NodeAttribute(node);
        throw new AttributeException(type);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Line;
    }
}
