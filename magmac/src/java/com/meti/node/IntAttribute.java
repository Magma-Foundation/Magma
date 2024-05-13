package com.meti.node;

import java.util.Optional;

public record IntAttribute(int value) implements Attribute {
    @Override
    public Optional<Integer> asInt() {
        return Optional.of(value);
    }
}
