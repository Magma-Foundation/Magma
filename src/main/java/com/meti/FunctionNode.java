package com.meti;

import java.util.Optional;

public class FunctionNode implements Node {
    private final String name;

    public FunctionNode(String name) {
        this.name = name;
    }

    @Override
    public boolean is(Object key) {
        return Key.Id == key;
    }

    @Override
    public Optional<Attribute> apply(Object key) {
        if(Key.Name == key) return Optional.of(new TextAttribute(name));
        return Optional.empty();
    }

    public enum Key {
        Id,
        Name
    }
}
