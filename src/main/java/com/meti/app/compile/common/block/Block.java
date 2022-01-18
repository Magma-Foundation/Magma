package com.meti.app.compile.common.block;

import com.meti.api.collect.java.List;
import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.EmptyStream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.attribute.NodesAttribute1;
import com.meti.app.compile.node.Node;

import java.util.Objects;
import java.util.stream.Stream;

public record Block(List<Node> children) implements Node {
    public Block() {
        this(List.createList());
    }

    public Block(Node... children) {
        this(List.apply(children));
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Children) return new NodesAttribute1(children);
        throw new AttributeException(type);
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) {
        return group == Attribute.Group.Nodes
                ? Stream.of(Attribute.Type.Children)
                : Stream.empty();
    }

    @Override
    public com.meti.api.collect.stream.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Nodes
                ? Streams.apply(Attribute.Type.Children)
                : new EmptyStream<>();
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Block;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        try {
            return attribute.asStreamOfNodes1()
                    .foldRight(new Builder(), Builder::add)
                    .build();
        } catch (StreamException e) {
            throw new AttributeException(e);
        }
    }

    @Override
    public String toString() {
        try {
            var childrenAsString = children.stream()
                    .map(Objects::toString)
                    .foldRight((current, next) -> current + "," + next)
                    .orElse("");
            return "{\"children\":[" + childrenAsString + "]}";
        } catch (StreamException e) {
            return "";
        }
    }

    public record Builder(List<Node> children) implements Node.Builder<Builder> {
        public Builder() {
            this(List.createList());
        }

        public Builder add(Node child) {
            return new Builder(children.add(child));
        }

        @Override
        public Node build() {
            return new Block(children());
        }

        @Override
        public Builder merge(Builder other) {
            return new Builder(children.addAll(other.children));
        }
    }
}
