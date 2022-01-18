package com.meti.compile.common;

import com.meti.api.collect.JavaList;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.node.Node;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Declaration(Node identity) implements Node {
    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Definition
                ? Stream.of(Attribute.Type.Identity)
                : Stream.empty();
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Identity) return new NodeAttribute(identity);
        throw new AttributeException(type);
    }

    @Override
    public com.meti.api.collect.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return new JavaList<>(apply(group).collect(Collectors.toList())).stream();
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Declaration;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return type == Attribute.Type.Identity
                ? new Declaration(attribute.asNode())
                : this;
    }
}
