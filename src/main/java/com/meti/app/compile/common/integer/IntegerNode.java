package com.meti.app.compile.common.integer;

import com.meti.api.collect.java.List;
import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.IntegerAttribute;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record IntegerNode(int value) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new IntegerAttribute(value);
        throw new AttributeException(type);
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode().add("value", value);
    }

    @Deprecated
    private Stream<Attribute.Type> apply2(Attribute.Group group) throws AttributeException {
        return Stream.empty();
    }

    @Override
    public com.meti.api.collect.stream.Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return List.createList(apply2(group).collect(Collectors.toList())).stream();
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Integer;
    }
}
