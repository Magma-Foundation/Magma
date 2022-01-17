package com.meti.compile.common.block;

import com.meti.collect.EmptyStream;
import com.meti.collect.JavaList;
import com.meti.collect.StreamException;
import com.meti.collect.Streams;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodesAttribute1;
import com.meti.compile.node.Node;

import java.util.Objects;
import java.util.stream.Stream;

public record Block(JavaList<Node> children) implements Node {
    public Block() {
        this(new JavaList<>());
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
    public com.meti.collect.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Nodes
                ? Streams.apply(Attribute.Type.Children)
                : new EmptyStream<>();
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

    public record Builder(JavaList<Node> children) implements Node.Builder<Builder> {
        public Builder() {
            this(new JavaList<>());
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
