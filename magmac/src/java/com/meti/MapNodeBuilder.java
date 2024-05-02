package com.meti;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapNodeBuilder {
    private final Map<String, String> map;

    public MapNodeBuilder() {
        this(Collections.emptyMap());
    }

    public MapNodeBuilder(Map<String, String> map) {
        this.map = map;
    }

    public MapNodeBuilder with(String key, String value) {
        var copy = new HashMap<>(map);
        copy.put(key, value);
        return new MapNodeBuilder(copy);
    }

    public Node complete() {
        return new MapNode(map);
    }
}