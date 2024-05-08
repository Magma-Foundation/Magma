package com.meti;

import java.util.Map;
import java.util.Optional;

public record MapNode(String type, Map<String, String> node) {
    public static Optional<MapNode> fromTuple(Tuple<Map<String, String>, Optional<String>> tuple) {
        return tuple.b().map(name -> new MapNode(name, tuple.a()));
    }
}