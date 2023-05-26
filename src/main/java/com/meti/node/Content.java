package com.meti.node;

import java.util.Optional;

public class Content implements Node {
    private final String value;

    public Content(String value) {
        this.value = value;
    }

    @Override
    public boolean is(Object key) {
        return key == Key.Id;
    }

    @Override
    public Optional<Attribute> apply(Object key) {
        if (key == Key.Value) return Optional.of(new StringAttribute(value));
        return Optional.empty();
    }

    @Override
    public String render() {
        return value;
    }

    public enum Key {Value, Id}
}
