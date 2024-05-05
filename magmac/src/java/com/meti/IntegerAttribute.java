package com.meti;

import java.util.Optional;

public class IntegerAttribute implements Attribute {
    private final int value;

    public IntegerAttribute(int value) {
        this.value = value;
    }

    @Override
    public Optional<Integer> asInteger() {
        return Optional.of(value);
    }
}
