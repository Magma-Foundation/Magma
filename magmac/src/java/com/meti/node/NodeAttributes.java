package com.meti.node;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class NodeAttributes {
    private final Map<String, Attribute> map;

    public NodeAttributes(Map<String, ? extends Attribute> map) {
        this.map = new HashMap<>(map);
    }

    public static NodeAttributes fromStrings(Map<String, String> map) {
        var copy = new HashMap<String, Attribute>();
        for (var stringStringEntry : map.entrySet()) {
            copy.put(stringStringEntry.getKey(), new StringAttribute(stringStringEntry.getValue()));
        }

        return new NodeAttributes(copy);
    }

    public Optional<Attribute> apply(String name) {
        if(map.containsKey(name)) {
            return Optional.of(map.get(name));
        }
        return Optional.empty();
    }

    public NodeAttributes add(NodeAttributes other) {
        var copy = new HashMap<>(map);
        copy.putAll(other.map);
        return new NodeAttributes(copy);
    }
}
