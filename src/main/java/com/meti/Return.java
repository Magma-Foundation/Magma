package com.meti;

import java.util.stream.Stream;

public record Return(Node value) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) {
        return new NodeAttribute(value);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Return;
    }

    @Override
    public Stream<Attribute.Type> stream(Attribute.Group group) {
        return Stream.of(Attribute.Type.Value);
    }

    @Override
    public Node with(Node value) {
        return new Return(value);
    }
}
