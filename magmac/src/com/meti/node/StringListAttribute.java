package com.meti.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StringListAttribute implements Attribute {
    private final List<String> values;

    public StringListAttribute(List<String> values) {
        this.values = values;
    }

    @Override
    public Attribute merge(Attribute value) {
        return value.asListOfStrings().map(list -> {
            var copy = new ArrayList<>(values);
            copy.addAll(list);
            return new StringListAttribute(copy);
        }).orElse(this);
    }

    @Override
    public Optional<List<String>> asListOfStrings() {
        return Optional.of(values);
    }
}
