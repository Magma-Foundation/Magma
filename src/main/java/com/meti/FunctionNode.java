package com.meti;

import java.util.Optional;

public class FunctionNode implements Node {
    @Override
    public boolean is(Object key) {
        return Key.Id == key;
    }

    @Override
    public Optional<Attribute> apply(Object key) {
        return Optional.empty();
    }

    public enum Key {
        Id
    }
}
