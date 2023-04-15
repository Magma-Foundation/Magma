package com.meti;

import java.util.Optional;

public class ClassNode implements Node {
    private final String name;



    public ClassNode(String name) {
        this.name = name;
    }

    enum Key {
        Id,
        Name
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
}
