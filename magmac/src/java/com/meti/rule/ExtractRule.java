package com.meti.rule;

import com.meti.Tuple;

import java.util.Map;
import java.util.Optional;

public record ExtractRule(String key) implements Rule {

    public static Rule $(String parent) {
        return new ExtractRule(parent);
    }

    @Override
    public Optional<Tuple<Map<String, String>, Optional<String>>> apply(String value) {
        return Optional.of(Map.of(key, value)).map(map -> new Tuple<>(map, Optional.empty()));
    }
}