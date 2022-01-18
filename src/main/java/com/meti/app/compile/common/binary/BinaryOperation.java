package com.meti.app.compile.common.binary;

import com.meti.api.collect.java.List;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.attribute.NodeAttribute;
import com.meti.app.compile.attribute.NodesAttribute;
import com.meti.app.compile.node.Node;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record BinaryOperation(Node operator, Node first, Node second) implements Node {
    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return switch (group) {
            case Node -> Stream.of(Attribute.Type.Operator);
            case Nodes -> Stream.of(Attribute.Type.Arguments);
            default -> Stream.empty();
        };
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Operator -> new NodeAttribute(operator);
            case Arguments -> new NodesAttribute(java.util.List.of(first, second));
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public com.meti.api.collect.stream.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return List.createList(apply(group).collect(Collectors.toList())).stream();
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Binary;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return switch (type) {
            case Operator -> new BinaryOperation(attribute.asNode(), first, second);
            case Arguments -> {
                var arguments = attribute.asStreamOfNodes().collect(Collectors.toList());
                yield new BinaryOperation(operator, arguments.get(0), arguments.get(1));
            }
            default -> this;
        };
    }
}
