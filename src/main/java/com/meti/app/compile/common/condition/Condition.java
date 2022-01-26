package com.meti.app.compile.common.condition;

import com.meti.api.collect.java.List;
import com.meti.api.json.JSONNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Condition(Type type, Node state, Node body) implements Node {
    private Stream<Attribute.Type> apply2(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Node
                ? Stream.of(Attribute.Type.Arguments, Attribute.Type.Value)
                : Stream.empty();
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Arguments -> new NodeAttribute(state);
            case Value -> new NodeAttribute(body);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public com.meti.api.collect.stream.Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return List.createList(apply2(group).collect(Collectors.toList())).stream();
    }

    @Override
    public boolean is(Type type) {
        return this.type == type;
    }

    @Override
    public JSONNode toJSON() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return switch (type) {
            case Arguments -> new Condition(this.type, attribute.asNode(), body);
            case Value -> new Condition(this.type, state, attribute.asNode());
            default -> this;
        };
    }
}
