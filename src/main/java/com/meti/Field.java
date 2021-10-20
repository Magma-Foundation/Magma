package com.meti;

import java.util.List;

public record Field(String name, String type) implements Node {
    @Override
    public boolean is(Type type) {
        return type == Type.Field;
    }

    @Override
    public Attribute apply(Attribute.Group group) {
        return switch (group) {
            case Name -> new StringAttribute(name);
            case Type -> new StringAttribute(type);
            default -> throw new UnsupportedOperationException("Unknown type: " + group);
        };
    }

    public String value() {
        throw new UnsupportedOperationException("Cannot render node of type: " + getClass());
    }

    public List<? extends Node> members() {
        throw new UnsupportedOperationException();
    }

    public Node body() {
        throw new UnsupportedOperationException();
    }

    public Node identity() {
        throw new UnsupportedOperationException();
    }
}