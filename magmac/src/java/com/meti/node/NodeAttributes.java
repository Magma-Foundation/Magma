package com.meti.node;

import java.util.HashMap;
import java.util.Map;

public final class NodeAttributes {
    private final Map<String, Attribute> map;

    private NodeAttributes(Map<String, Attribute> map) {
        this.map = map;
    }

    public static NodeAttributes fromStrings(Map<String, String> map) {
        var copy = new HashMap<String, Attribute>();
        for (var stringStringEntry : map.entrySet()) {
            copy.put(stringStringEntry.getKey(), new StringAttribute(stringStringEntry.getValue()));
        }

        return new NodeAttributes(copy);
    }

    @Deprecated
    public Map<String, String> map() {
        var copy = new HashMap<String, String>();
        for (var stringStringEntry : map.entrySet()) {
            copy.put(stringStringEntry.getKey(), stringStringEntry.getValue().asString().orElse(""));
        }

        return copy;
    }
}
