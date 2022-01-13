package com.meti.compile.magma;

import com.meti.collect.JavaList;
import com.meti.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.NodesAttribute;
import com.meti.compile.node.Node;

import java.util.List;
import java.util.stream.Stream;

public record FunctionType(Node returns, JavaList<Node> parameters) implements Node {
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
    public boolean is(Type type) {
        return type == Type.Function;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException, StreamException {
        return switch (type) {
            case Type -> new FunctionType(attribute.asNode(), parameters);
            case Parameters -> new FunctionType(returns, attribute.asStreamOfNodes().foldRight(new JavaList<>(), JavaList::add));
            default -> throw new AttributeException(type);
        };
    }
}