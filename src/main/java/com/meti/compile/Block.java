package com.meti.compile;

import java.util.List;
import java.util.stream.Stream;

class Block implements Node {
    private final List<Node> values;

    public Block(List<Node> values) {
        this.values = values;
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) {
        return group == Attribute.Group.Nodes
                ? Stream.of(Attribute.Type.Children)
                : Stream.empty();
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Children) return new ListNodeAttribute(values);
        throw new AttributeException(type);
    }

    @Override
    public boolean is(Type type) {
        return type == Node.Type.Block;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return new Block(attribute.streamNodes().toList());
    }
}
