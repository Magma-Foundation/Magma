package com.meti.app.node;

import com.meti.api.stream.StreamException;
import com.meti.app.attribute.Attribute;
import com.meti.app.attribute.AttributeException;
import com.meti.app.attribute.NodesAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class Container implements Node {
    protected final List<? extends Node> children;

    public Container(List<? extends Node> children) {
        this.children = children;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Children) return new NodesAttribute(children);
        throw new AttributeException(type);
    }

    @Override
    public java.util.stream.Stream<Attribute.Type> stream(Attribute.Group group) {
        return group == Attribute.Group.Nodes
                ? java.util.stream.Stream.of(Attribute.Type.Children)
                : Stream.empty();
    }

    @Override
    public Node with(Attribute.Type type, Attribute value) throws AttributeException {
        try {
            var nodes = value.asNodeStream().foldRight(new ArrayList<Node>(), (collection, node) -> {
                collection.add(node);
                return collection;
            });
            return complete(nodes);
        } catch (StreamException e) {
            throw new AttributeException("Failed to fold children.", e);
        }
    }

    protected abstract Node complete(List<Node> nodes);
}
