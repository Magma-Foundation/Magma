package com.meti.app.compile.node;

import com.meti.api.collect.java.List;
import com.meti.api.json.JSONNode;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.InputAttribute;
import com.meti.app.compile.text.RootText;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Primitive implements Node {
    Bool,
    Void;

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Name) return new InputAttribute(new RootText(name()));
        throw new AttributeException(type);
    }

    @Override
    public JSONNode toJSON() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "\"" + name() + "\"";
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
        return type == Type.Primitive;
    }
}
