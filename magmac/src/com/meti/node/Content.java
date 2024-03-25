package com.meti.node;

import java.util.Optional;

public record Content(String name, String value, int indent) implements Node {
    public static final String VALUE = "value";
    public static final String NAME = "name";

    @Override
    public Optional<Attribute> apply(String name) {
        if (name.equals("name")) return Optional.of(new StringAttribute(name));
        if (name.equals("value")) return Optional.of(new StringAttribute(value));
        if (name.equals("indent")) return Optional.of(new IntAttribute(indent));
        return Optional.empty();
    }
}