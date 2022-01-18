package com.meti.app.compile.node;

import com.meti.api.collect.JavaList;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.attribute.TextAttribute;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Primitive implements Node {
    Bool,
    Void;

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Name) return new TextAttribute(new Text(name()));
        throw new AttributeException(type);
    }

    @Override
    public String toString() {
        return "\"" + name() + "\"";
    }

    @Override
    @Deprecated
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return Stream.empty();
    }

    @Override
    public com.meti.api.collect.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return new JavaList<>(apply(group).collect(Collectors.toList())).stream();
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Primitive;
    }
}
