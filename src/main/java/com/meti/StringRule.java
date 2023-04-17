package com.meti;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class StringRule implements Rule {
    private final String value;

    public StringRule(String value) {
        this.value = value;
    }

    @Override
    public Optional<Map<String, String>> extract(String value) {
        if(this.value.equals(value)) {
            return Optional.of(Collections.emptyMap());
        }
        return Optional.empty();
    }
}
