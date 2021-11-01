package com.meti.app.compile.feature;

import com.meti.api.StreamException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeListAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record Block(List<? extends Node> children) implements Node {
    @Override
    public boolean is(Type type) {
        return type == Type.Block;
    }

    @Override
    public Stream<Attribute.Type> stream(Attribute.Group group) {
        return group == Attribute.Group.NodeList
                ? Stream.of(Attribute.Type.Children)
                : Stream.empty();
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        try {
            if (type == Attribute.Type.Children) {
                var newChildren = attribute.asNodeStream().foldRight(new ArrayList<>(), this::merge);
                return new Block(newChildren);
            }
            return this;
        } catch (StreamException e) {
            throw new AttributeException(e);
        }
    }

    private ArrayList<Node> merge(ArrayList<Node> sum, Node next) {
        sum.add(next);
        return sum;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Children) return new NodeListAttribute(children);
        throw new AttributeException("Unknown type: " + type);
    }
}
