package com.meti.app.compile.common.invoke;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.attribute.NodeAttribute;
import com.meti.app.compile.attribute.NodesAttribute1;
import com.meti.app.compile.node.Node;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Invocation implements Node {
    private final Node caller;
    private final List<Node> arguments;

    public Invocation(Node caller, List<Node> arguments) {
        this.caller = caller;
        this.arguments = arguments;
    }

    public Invocation(Node caller, Node... arguments) {
        this.caller = caller;
        this.arguments = List.apply(arguments);
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Caller -> new NodeAttribute(caller);
            case Arguments -> new NodesAttribute1(arguments);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return switch (group) {
            case Node -> Stream.of(Attribute.Type.Caller);
            case Nodes -> Stream.of(Attribute.Type.Arguments);
            default -> Stream.empty();
        };
    }

    @Override
    public com.meti.api.collect.stream.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return new List<>(apply(group).collect(Collectors.toList())).stream();
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Invocation;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        try {
            return switch (type) {
                case Caller -> new Invocation(attribute.asNode(), arguments);
                case Arguments -> new Invocation(caller, attribute
                        .asStreamOfNodes1()
                        .foldRight(new List<>(), List::add));
                default -> this;
            };
        } catch (StreamException e) {
            throw new AttributeException(e);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(caller, arguments);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invocation that)) return false;
        return Objects.equals(caller, that.caller) && Objects.equals(arguments, that.arguments);
    }

    @Override
    public String toString() {
        return "{" +
               "\n\t\"caller\":" + caller +
               ",\n\t\"arguments\":" + arguments +
               '}';
    }
}
