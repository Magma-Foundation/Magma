package com.meti.node;

import com.meti.Tuple;

import java.util.Map;
import java.util.Optional;

public record MapNode(String type, Map<String, String> node) {
    public static Optional<MapNode> fromTuple(Tuple<Map<String, String>, Optional<String>> tuple) {
        return tuple.right().map(name -> new MapNode(name, tuple.left()));
    }
}