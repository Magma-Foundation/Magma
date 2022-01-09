package com.meti.compile;

import java.util.stream.Stream;

record Function(Node identity, Content body) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Identity -> new NodeAttribute(identity);
            case Value -> new NodeAttribute(body);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return new Function(attribute.asNode(), body);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Function;
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) {
        return group == Attribute.Group.Field
                ? Stream.of(Attribute.Type.Identity)
                : Stream.empty();
    }
}
