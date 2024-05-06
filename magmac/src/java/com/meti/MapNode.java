package com.meti;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public record MapNode(String name, Map<String, Attribute> map) implements Node {
    public MapNode(String name) {
        this(name, Collections.emptyMap());
    }

    @Override
    public Optional<Attribute> apply(String key) {
        if (map.containsKey(key)) {
            return Optional.of(map.get(key));
        } else {
            return Optional.empty();
        }
    }
}
