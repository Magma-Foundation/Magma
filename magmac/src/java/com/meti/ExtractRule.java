package com.meti;

import java.util.Map;
import java.util.Optional;

public record ExtractRule(String key) implements Rule {

    @Override
    public Optional<Tuple<Map<String, String>, Optional<String>>> apply(String value) {
        return Optional.of(Map.of(key, value)).map(map -> new Tuple<>(map, Optional.empty()));
    }
}