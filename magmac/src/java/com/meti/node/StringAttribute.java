package com.meti.node;

import java.util.Optional;

public record StringAttribute(String value) implements Attribute {
    @Override
    public Optional<String> asString() {
        return Optional.of(value);
    }
}
