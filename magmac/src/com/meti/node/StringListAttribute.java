package com.meti.node;

import java.util.List;
import java.util.Optional;

public class StringListAttribute implements Attribute {
    private final List<String> values;

    public StringListAttribute(List<String> values) {
        this.values = values;
    }

    @Override
    public Optional<List<String>> asListOfStrings() {
        return Optional.of(values);
    }
}
