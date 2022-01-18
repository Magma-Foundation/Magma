package com.meti.compile.cache;

import com.meti.api.collect.CollectionException;
import com.meti.api.collect.JavaList;
import com.meti.api.collect.StreamException;
import com.meti.api.core.F1;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.NodesAttribute1;
import com.meti.compile.node.Node;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Cache(Node value, JavaList<Node> children) implements Node {
    public Cache(Node value, Node... children) {
        this(value, JavaList.apply(children));
    }

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
    public com.meti.api.collect.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
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

    @Deprecated
    public record Builder(Node value, JavaList<Node> children) {
        public Builder(Node value) {
            this(value, new JavaList<>());
        }

        public Builder add(Node child) {
            return new Builder(value, children.add(child));
        }

        public Node build() {
            return new Cache(value, children);
        }
    }

    @Deprecated
    public record Prototype<T extends Node.Builder<T>>(T builder, JavaList<Node> items) {
        public Prototype(T builder, Node... items) {
            this(builder, JavaList.apply(items));
        }

        public static <T extends Node.Builder<T>, E extends Exception> F1<F1<Node, Prototype<T>, E>, Prototype<T>, E> fromCache(Node cache) throws CollectionException, AttributeException {
            var value = cache.apply(Attribute.Type.Value).asNode();
            var children = cache.apply(Attribute.Type.Children)
                    .asStreamOfNodes1()
                    .foldRight(new JavaList<Node>(), JavaList::add);
            return builderSupplier -> {
                var prototype = builderSupplier.apply(value);
                return prototype.append(children);
            };
        }

        public Prototype<T> append(JavaList<Node> children) {
            return new Prototype<>(builder, items.addAll(children));
        }

        public Prototype<T> append(Prototype<T> other) {
            var newBuilder = builder.merge(other.builder);
            var newItems = items.addAll(other.items);
            return new Prototype<>(newBuilder, newItems);
        }

        public Node toCache() {
            var built = builder.build();
            return items.isEmpty() ? built : new Cache(built, items);
        }
    }
}
