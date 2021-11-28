package com.meti.app.node;

import com.meti.app.attribute.Attribute;
import com.meti.app.attribute.AttributeException;
import com.meti.app.attribute.NodeAttribute;

import java.util.stream.Stream;

public record Function(Node identity, Node value) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Identity -> new NodeAttribute(identity);
            case Value -> new NodeAttribute(value);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Function;
    }

    @Override
    public Stream<Attribute.Type> stream(Attribute.Group group) {
        return switch (group) {
            case Field -> Stream.of(Attribute.Type.Identity);
            case Node -> Stream.of(Attribute.Type.Value);
            default -> Stream.empty();
        };
    }

    @Override
    public Node with(Attribute.Type type, Attribute value) throws AttributeException {
        return switch (type) {
            case Identity -> new Function(value.asNode(), this.value);
            case Value -> new Function(identity, value.asNode());
            default -> this;
        };
    }
}
