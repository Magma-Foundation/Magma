package com.meti;

import java.util.Map;
import java.util.Optional;

public record RequireLeft(String slice, LastIndexOfRule right) implements Rule {
    private Optional<Map<String, String>> apply1(String stripped) {
        Optional<Map<String, String>> empty;
        if (!stripped.startsWith(slice())) {
            empty = Optional.empty();
        } else {
            var segments = stripped.substring(slice().length());
            empty = this.right().apply(segments).map(tuple -> tuple.a());
        }
        return empty;
    }

    @Override
    public Optional<Tuple<Map<String, String>, Optional<String>>> apply(String value) {
        return apply1(value).map(map -> new Tuple<>(map, Optional.empty()));
    }
}