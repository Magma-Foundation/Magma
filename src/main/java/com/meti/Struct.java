package com.meti;

import java.util.Optional;

public class Struct implements Node{
    @Override
    public boolean is(Object key) {
        return Key.Id == key;
    }

    public enum Key {
        Id
    }

    @Override
    public Optional<Attribute> apply(Object key) {
        return Optional.empty();
    }
}
