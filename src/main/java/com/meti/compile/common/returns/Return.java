package com.meti.compile.common.returns;

import com.meti.api.collect.JavaList;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.node.Node;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Return(Node value) implements Node {
    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Node
                ? Stream.of(Attribute.Type.Value)
                : Stream.empty();
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new NodeAttribute(value);
        throw new AttributeException(type);
    }

    @Override
    public com.meti.api.collect.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return new JavaList<>(apply(group).collect(Collectors.toList())).stream();
    }

    @Override
    public boolean is(Type type) {
        return type == Node.Type.Return;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return new Return(attribute.asNode());
    }
}
