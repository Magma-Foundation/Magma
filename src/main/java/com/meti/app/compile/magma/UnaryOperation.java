package com.meti.app.compile.magma;

import com.meti.api.collect.java.List;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record UnaryOperation(Node caller, Node callee) implements Node {
    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Node
                ? Stream.of(Attribute.Type.Caller, Attribute.Type.Arguments)
                : Stream.empty();
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Caller -> new NodeAttribute(caller);
            case Arguments -> new NodeAttribute(callee);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public com.meti.api.collect.stream.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return List.createList(apply(group).collect(Collectors.toList())).stream();
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Unary;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return switch (type) {
            case Caller -> new UnaryOperation(attribute.asNode(), callee);
            case Arguments -> new UnaryOperation(caller, attribute.asNode());
            default -> this;
        };
    }
}
