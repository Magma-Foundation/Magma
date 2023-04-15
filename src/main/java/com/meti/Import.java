package com.meti;

import java.util.List;
import java.util.Optional;

public record Import(List<String> values) implements Node {
    public enum Key {
        Id,
        Values
    }

    @Override
    public boolean is(Object key) {
        return Key.Id == key;
    }

    @Override
    public Optional<Attribute> apply(Object key) {
        if (Key.Values == key) {
            return Optional.of(new TextListAttribute(values));
        } else {
            return Optional.empty();
        }
    }
}
