package com.meti.app.compile.feature;

import com.meti.app.compile.node.Input;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.InputAttribute;
import com.meti.app.compile.node.attribute.NodeAttribute;

import java.util.stream.Stream;

public record EmptyField(Input name, Node type) implements Node {
    @Override
    public boolean is(Type type) {
        return type == Type.Field;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        if (type == Attribute.Type.Type) return new EmptyField(name, attribute.asNode());
        return this;
    }

    @Override
    public Stream<Attribute.Type> stream(Attribute.Group group) {
        if (group == Attribute.Group.Type) return Stream.of(Attribute.Type.Type);
        return Stream.empty();
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Name -> new InputAttribute(name);
            case Type -> new NodeAttribute(this.type);
            default -> throw new AttributeException(type.name());
        };
    }
}
