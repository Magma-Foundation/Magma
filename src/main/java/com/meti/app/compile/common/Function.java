package com.meti.app.compile.common;

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

public abstract class Function implements Node {
    protected final Node identity;
    protected final List<Node> parameters;

    public Function(Node identity, List<Node> parameters) {
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
            case Definition -> Stream.of(Attribute.Type.Identity);
            default -> Stream.empty();
        };
    }

    @Override
    public com.meti.api.collect.stream.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return List.createList(apply(group).collect(Collectors.toList())).stream();
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        try {
            return switch (type) {
                case Identity -> complete(attribute.asNode(), parameters);
                case Parameters -> complete(identity, attribute.asStreamOfNodes1()
                        .foldRight(List.createList(), List::add));
                default -> throw new AttributeException(type);
            };
        } catch (StreamException e) {
            throw new AttributeException(e);
        }
    }

    protected abstract Node complete(Node node, List<Node> parameters);

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
