package com.meti.app.compile.magma;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Type;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.NodesAttribute;

public record FunctionType(Node returns, List<Node> parameters) implements Type {
    public FunctionType(Node returns, Node... parameters) {
        this(returns, List.apply(parameters));
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return switch (group) {
            case Type -> Streams.apply(Attribute.Type.Type);
            case Types -> Streams.apply(Attribute.Type.Parameters);
            default -> Streams.empty();
        };
    }

    @Override
    public boolean is(Category category) {
        return category == Category.Function;
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
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        try {
            return switch (type) {
                case Type -> new FunctionType(attribute.asNode(), parameters);
                case Parameters -> new FunctionType(returns, attribute.asStreamOfNodes()
                        .foldRight(List.createList(), List::add));
                default -> throw new AttributeException(type);
            };
        } catch (StreamException e) {
            return this;
        }
    }
}