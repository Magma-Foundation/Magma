package com.meti.compile.common;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.node.Node;

import java.util.stream.Stream;

public record Reference(Node value) implements Node {
    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Type
                ? Stream.of(Attribute.Type.Value)
                : Stream.empty();
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new NodeAttribute(value);
        throw new AttributeException(type);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Reference;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return type == Attribute.Type.Value
                ? new Reference(attribute.asNode())
                : this;
    }
}
