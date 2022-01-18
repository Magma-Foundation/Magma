package com.meti.app.compile.magma;

import com.meti.api.collect.java.List;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.attribute.NodeAttribute;
import com.meti.app.compile.attribute.NodesAttribute;
import com.meti.app.compile.node.Node;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record FunctionType(Node returns, java.util.List parameters) implements Node {
    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
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
    public com.meti.api.collect.stream.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return new List<>(apply(group).collect(Collectors.toList())).stream();
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