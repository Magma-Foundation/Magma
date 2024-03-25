package com.meti.node;

import java.util.Optional;

public class IntAttribute implements Attribute {
    private final int value;

    public IntAttribute(int value) {
        this.value = value;
    }

    @Override
    public Optional<Integer> asInt() {
        return Optional.of(value);
    }

    @Override
    public String toString() {
        return "IntAttribute{" +
               "value=" + value +
               '}';
    }
}
