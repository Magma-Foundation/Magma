package com.meti.app.compile.common.integer;

import com.meti.api.collect.java.List;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.BooleanAttribute;
import com.meti.app.compile.node.attribute.IntegerAttribute;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record IntegerType(boolean signed, int bits) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Sign -> new BooleanAttribute(signed);
            case Bits -> new IntegerAttribute(bits);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    @Deprecated
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return Stream.empty();
    }

    @Override
    public com.meti.api.collect.stream.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return List.createList(apply(group).collect(Collectors.toList())).stream();
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Integer;
    }
}
