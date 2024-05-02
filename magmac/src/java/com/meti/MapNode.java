package com.meti;

import java.util.Map;
import java.util.Optional;

public record MapNode(Map<String, String> map) implements Node {
    @Override
    public Optional<String> find(String key) {
        if (map.containsKey(key)) {
            return Optional.of(map.get(key));
        } else {
            return Optional.empty();
        }
    }
}
