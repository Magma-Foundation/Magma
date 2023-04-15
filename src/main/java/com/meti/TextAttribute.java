package com.meti;

import java.util.Optional;

public class TextAttribute implements Attribute {
    private final String name;

    public TextAttribute(String name) {
        this.name = name;
    }

    @Override
    public Optional<String> asText() {
        return Optional.of(name);
    }
}
