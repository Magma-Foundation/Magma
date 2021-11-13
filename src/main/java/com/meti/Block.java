package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record Block(List<Node> children) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Children) return new NodesAttribute(children);
        throw new AttributeException(type);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Block;
    }

    @Override
    public Stream<Attribute.Type> stream(Attribute.Group group) {
        return group == Attribute.Group.Nodes
                ? Stream.of(Attribute.Type.Children)
                : Stream.empty();
    }

    @Override
    public Node with(Attribute.Type type, Attribute value) throws AttributeException {
        try {
            var nodes = value.asNodeStream().foldRight(new ArrayList<Node>(), (collection, node) -> {
                collection.add(node);
                return collection;
            });
            return new Block(nodes);
        } catch (StreamException e) {
            throw new AttributeException("Failed to fold children.", e);
        }
    }
}
