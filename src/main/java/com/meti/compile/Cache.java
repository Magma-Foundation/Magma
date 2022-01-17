package com.meti.compile;

import com.meti.collect.CollectionException;
import com.meti.collect.JavaList;
import com.meti.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.NodesAttribute1;
import com.meti.compile.node.Node;
import com.meti.core.F1;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Cache(Node value, JavaList<Node> children) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Value -> new NodeAttribute(value);
            case Children -> new NodesAttribute1(children);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return switch (group) {
            case Node -> Stream.of(Attribute.Type.Value);
            case Nodes -> Stream.of(Attribute.Type.Children);
            default -> Stream.empty();
        };
    }

    @Override
    public com.meti.collect.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return new JavaList<>(apply(group).collect(Collectors.toList())).stream();
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Cache;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        try {
            return switch (type) {
                case Value -> new Cache(attribute.asNode(), children);
                case Children -> new Cache(value, attribute.asStreamOfNodes1()
                        .foldRight(new JavaList<>(), JavaList::add));
                default -> this;
            };
        } catch (StreamException e) {
            throw new AttributeException(e);
        }
    }

    @Override
    public String toString() {
        return "{" +
               "\n\t\"value\":" + value +
               ",\n\t\"children\":" + children +
               '}';
    }

    public record Builder(Node value, JavaList<Node> children) {
        public Builder(Node value) {
            this(value, new JavaList<>());
        }

        Builder add(Node child) {
            return new Builder(value, children.add(child));
        }

        Node build() {
            return new Cache(value, children);
        }
    }

    public record Prototype<T extends Node.Builder<T>>(T builder, JavaList<Node> items) {
        public Prototype(T builder, Node... items) {
            this(builder, JavaList.apply(items));
        }

        public static <T extends Node.Builder<T>, E extends Exception> F1<F1<Node, T, E>, Prototype<T>, E> fromCache(Node cache) throws CollectionException, AttributeException {
            var value = cache.apply(Attribute.Type.Value).asNode();
            var children = cache.apply(Attribute.Type.Children)
                    .asStreamOfNodes1()
                    .foldRight(new JavaList<Node>(), JavaList::add);
            return builderSupplier -> {
                var builder = builderSupplier.apply(value);
                return new Prototype<T>(builder, children);
            };
        }

        public Prototype<T> merge(Prototype<T> other) {
            var newBuilder = builder.merge(other.builder);
            var newItems = items.addAll(other.items);
            return new Prototype<>(newBuilder, newItems);
        }

        Node toCache() {
            var built = builder.build();
            return items.isEmpty() ? built : new Cache(built, items);
        }
    }
}
