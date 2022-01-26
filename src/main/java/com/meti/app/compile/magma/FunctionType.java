package com.meti.app.compile.magma;

import com.meti.api.collect.java.List;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.NodesAttribute;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record FunctionType(Node returns, java.util.List parameters) implements Node {
    private Stream<Attribute.Type> apply2(Attribute.Group group) throws AttributeException {
        return switch (group) {
            case Type -> Stream.of(Attribute.Type.Type);
            case Types -> Stream.of(Attribute.Type.Parameters);
            default -> Stream.empty();
        };
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Type -> new NodeAttribute(returns);
            case Parameters -> new NodesAttribute(parameters);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public com.meti.api.collect.stream.Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return List.createList(apply2(group).collect(Collectors.toList())).stream();
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Function;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return switch (type) {
            case Type -> new FunctionType(attribute.asNode(), parameters);
            case Parameters -> new FunctionType(returns, attribute.asStreamOfNodes().collect(Collectors.toList()));
            default -> throw new AttributeException(type);
        };
    }
}