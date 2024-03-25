package com.meti.node;

import java.util.Optional;

public class StringAttribute implements Attribute{
    private final String value;

    public StringAttribute(String value) {
        this.value = value;
    }

    @Override
    public Optional<String> asString() {
        return Optional.of(value);
    }
}
