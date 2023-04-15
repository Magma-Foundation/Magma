package com.meti;

import java.util.List;
import java.util.Optional;

public final class TextListAttribute implements Attribute {
    private final List<String> text;

    public TextListAttribute(List<String> text) {
        this.text = text;
    }

    @Override
    public Optional<List<String>> asTextList() {
        return Optional.of(text);
    }
}