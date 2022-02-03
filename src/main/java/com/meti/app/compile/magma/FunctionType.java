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
    public Stream<Attribute.Category> apply(Attribute.Group group) throws AttributeException {
        return switch (group) {
            case Type -> Streams.apply(Attribute.Category.Type);
            case Types -> Streams.apply(Attribute.Category.Parameters);
            default -> Streams.empty();
        };
    }

    @Override
    public boolean is(Category category) {
        return category == Node.Category.Function;
    }

    @Override
    public Attribute apply(Attribute.Category category) throws AttributeException {
        return switch (category) {
            case Type -> new NodeAttribute(returns);
            case Parameters -> new NodesAttribute(parameters);
            default -> throw new AttributeException(category);
        };
    }

    @Override
    public Node with(Attribute.Category category, Attribute attribute) throws AttributeException {
        try {
            return switch (category) {
                case Type -> new FunctionType(attribute.asNode(), parameters);
                case Parameters -> new FunctionType(returns, attribute.asStreamOfNodes()
                        .foldRight(List.createList(), List::add));
                default -> throw new AttributeException(category);
            };
        } catch (StreamException e) {
            return this;
        }
    }
}