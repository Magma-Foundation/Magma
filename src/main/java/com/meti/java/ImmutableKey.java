package com.meti.java;

import java.util.function.Function;

public record ImmutableKey<K>(K value) implements Key<K> {
    @Override
    public <R> R peek(Function<K, R> mapper) {
        return mapper.apply(value);
    }

    @Override
    public K unwrap() {
        return value;
    }
}
