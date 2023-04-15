package com.meti;

import java.util.Optional;

public class ClassNode implements Node {
    enum Key {
        Id
    }

    @Override
    public boolean is(Object key) {
        return Key.Id == key;
    }

    @Override
    public Optional<Attribute> apply(Object key) {
        return Optional.empty();
    }
}
