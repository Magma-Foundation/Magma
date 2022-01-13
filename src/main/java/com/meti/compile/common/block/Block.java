package com.meti.compile.common.block;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodesAttribute;
import com.meti.compile.node.Node;

import java.util.List;
import java.util.stream.Stream;

record Block(List<Node> values) implements Node {
    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) {
        return group == Attribute.Group.Nodes
                ? Stream.of(Attribute.Type.Children)
                : Stream.empty();
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Children) return new NodesAttribute(values);
        throw new AttributeException(type);
    }

    @Override
    public boolean is(Type type) {
        return type == Node.Type.Block;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return new Block(attribute.asStreamOfNodes()
                .foldRight(Stream.<Node>builder(), Stream.Builder::add)
                .build().toList());
    }
}
