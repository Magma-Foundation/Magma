package com.meti;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class PlaceholderRule implements Rule {
    private final String name;

    public PlaceholderRule(String name) {
        this.name = name;
    }

    @Override
    public Optional<Map<String, String>> extract(String value) {
        return Optional.of(Collections.singletonMap(name, value));
    }
}
