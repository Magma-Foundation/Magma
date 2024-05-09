package com.meti.node;

import java.util.List;
import java.util.Optional;

public record StringListAttribute(List<String> values) implements Attribute {
    @Override
    public Optional<List<String>> asListOfStrings() {
        return Optional.of(values);
    }
}
