package com.meti.compile.common.string;

import com.meti.api.collect.JavaList;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.TextAttribute;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record String(Text value) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new TextAttribute(value);
        throw new AttributeException(type);
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
        return type == Type.String;
    }
}
