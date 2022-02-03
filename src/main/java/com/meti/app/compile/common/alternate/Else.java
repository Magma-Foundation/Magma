package com.meti.app.compile.common.alternate;

import com.meti.api.collect.java.List;
import com.meti.api.json.JSONNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Else(Node value) implements Node {
    private Stream<Attribute.Category> apply2(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Node
                ? Stream.of(Attribute.Category.Value)
                : Stream.empty();
    }

    @Override
    public Attribute apply(Attribute.Category category) throws AttributeException {
        if (category == Attribute.Category.Value) return new NodeAttribute(value);
        throw new AttributeException(category);
    }

    @Override
    public com.meti.api.collect.stream.Stream<Attribute.Category> apply(Attribute.Group group) throws AttributeException {
        return List.createList(apply2(group).collect(Collectors.toList())).stream();
    }

    @Override
    public boolean is(Category category) {
        return category == Node.Category.Else;
    }

    @Override
    public JSONNode toJSON() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node with(Attribute.Category category, Attribute attribute) throws AttributeException {
        return category == Attribute.Category.Value
                ? new Else(attribute.asNode())
                : this;
    }
}
