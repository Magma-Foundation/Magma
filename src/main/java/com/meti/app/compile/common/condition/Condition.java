package com.meti.app.compile.common.condition;

import com.meti.api.collect.java.List;
import com.meti.api.json.JSONNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Condition(Category category, Node state, Node body) implements Node {
    private Stream<Attribute.Category> apply2(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Node
                ? Stream.of(Attribute.Category.Arguments, Attribute.Category.Value)
                : Stream.empty();
    }

    @Override
    public Attribute apply(Attribute.Category category) throws AttributeException {
        return switch (category) {
            case Arguments -> new NodeAttribute(state);
            case Value -> new NodeAttribute(body);
            default -> throw new AttributeException(category);
        };
    }

    @Override
    public com.meti.api.collect.stream.Stream<Attribute.Category> apply(Attribute.Group group) throws AttributeException {
        return List.createList(apply2(group).collect(Collectors.toList())).stream();
    }

    @Override
    public boolean is(Category category) {
        return this.category == category;
    }

    @Override
    public JSONNode toJSON() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node with(Attribute.Category category, Attribute attribute) throws AttributeException {
        return switch (category) {
            case Arguments -> new Condition(this.category, attribute.asNode(), body);
            case Value -> new Condition(this.category, state, attribute.asNode());
            default -> this;
        };
    }
}
