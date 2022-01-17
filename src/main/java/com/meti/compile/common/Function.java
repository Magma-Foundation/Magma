package com.meti.compile.common;

import com.meti.collect.JavaList;
import com.meti.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.NodesAttribute1;
import com.meti.compile.node.Node;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Function implements Node {
    protected final Node identity;
    protected final JavaList<Node> parameters;

    public Function(Node identity, JavaList<Node> parameters) {
        this.identity = identity;
        this.parameters = parameters;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Identity -> new NodeAttribute(identity);
            case Parameters -> new NodesAttribute1(parameters);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return switch (group) {
            case Declarations -> Stream.of(Attribute.Type.Parameters);
            case Declaration -> Stream.of(Attribute.Type.Identity);
            default -> Stream.empty();
        };
    }

    @Override
    public com.meti.collect.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return new JavaList<>(apply(group).collect(Collectors.toList())).stream();
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        try {
            return switch (type) {
                case Identity -> complete(attribute.asNode(), parameters);
                case Parameters -> complete(identity, attribute.asStreamOfNodes1()
                        .foldRight(new JavaList<>(), JavaList::add));
                default -> throw new AttributeException(type);
            };
        } catch (StreamException e) {
            throw new AttributeException(e);
        }
    }

    protected abstract Node complete(Node node, JavaList<Node> parameters);

    @Override
    public int hashCode() {
        return Objects.hash(identity, parameters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Function function)) return false;
        return Objects.equals(identity, function.identity) && Objects.equals(parameters, function.parameters);
    }
}
