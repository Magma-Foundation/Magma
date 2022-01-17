package com.meti.compile.node;

import com.meti.collect.JavaList;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmptyNode implements Node {
    public static final EmptyNode EmptyNode_ = new EmptyNode();

    private EmptyNode() {
    }

    @Override
    @Deprecated
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return Stream.empty();
    }

    @Override
    public com.meti.collect.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return new JavaList<>(apply(group).collect(Collectors.toList())).stream();
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Empty;
    }
}
