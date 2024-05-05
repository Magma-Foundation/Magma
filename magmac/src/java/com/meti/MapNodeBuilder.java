package com.meti;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapNodeBuilder {
    public static final MapNodeBuilder Empty = new MapNodeBuilder();
    private final Map<String, Attribute> map;

    private MapNodeBuilder() {
        this(Collections.emptyMap());
    }

    public MapNodeBuilder(Map<String, Attribute> map) {
        this.map = map;
    }

    public MapNodeBuilder string(String key, String value) {
        return with(key, new StringAttribute(value));
    }

    public MapNodeBuilder integer(String key, int value) {
        return with(key, new IntegerAttribute(value));
    }

    private MapNodeBuilder with(String key, Attribute attribute) {
        var copy = new HashMap<>(map);
        copy.put(key, attribute);
        return new MapNodeBuilder(copy);
    }

    public Node build() {
        return new MapNode(map);
    }
}