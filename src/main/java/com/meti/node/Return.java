package com.meti.node;

import com.meti.attribute.Attribute;
import com.meti.attribute.AttributeException;
import com.meti.attribute.NodeAttribute;

import java.util.stream.Stream;

public record Return(Node value) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) {
        return new NodeAttribute(value);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Return;
    }

    @Override
    public Stream<Attribute.Type> stream(Attribute.Group group) {
        return group == Attribute.Group.Node
                ? Stream.of(Attribute.Type.Value)
                : Stream.empty();
    }

    @Override
    public Node with(Attribute.Type type, Attribute value) throws AttributeException {
        return type == Attribute.Type.Value ? new Return(value.asNode()) : this;
    }
}
