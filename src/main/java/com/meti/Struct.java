package com.meti;

import java.util.Optional;

public class Struct implements Node{
    private final String name;

    public Struct(String name) {
        this.name = name;
    }

    @Override
    public boolean is(Object key) {
        return Key.Id == key;
    }

    public enum Key {
        Id,
        Name
    }

    @Override
    public Optional<Attribute> apply(Object key) {
        if(Key.Name == key) return Optional.of(new TextAttribute(name));
        return Optional.empty();
    }
}
