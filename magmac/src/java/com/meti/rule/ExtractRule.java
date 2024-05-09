package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.NodeAttributes;

import java.util.Map;
import java.util.Optional;

public record ExtractRule(String key) implements Rule {

    public static Rule $(String parent) {
        return new ExtractRule(parent);
    }

    private Optional<Tuple<Map<String, String>, Optional<String>>> apply2(String value) {
        return Optional.of(Map.of(key, value)).map(map -> new Tuple<>(map, Optional.empty()));
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> apply(String value) {
        return apply2(value).map(tuple -> tuple.mapLeft(NodeAttributes::fromStrings));
    }
}