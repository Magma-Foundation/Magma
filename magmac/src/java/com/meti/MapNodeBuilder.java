package com.meti;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapNodeBuilder {
    private final Map<String, Attribute> map;

    public MapNodeBuilder() {
        this(Collections.emptyMap());
    }

    public MapNodeBuilder(Map<String, Attribute> map) {
        this.map = map;
    }

    public MapNodeBuilder withString(String key, String value) {
        return with(key, new StringAttribute(value));
    }

    public MapNodeBuilder withInteger(String key, int value) {
        return with(key, new IntegerAttribute(value));
    }

    private MapNodeBuilder with(String key, Attribute attribute) {
        var copy = new HashMap<>(map);
        copy.put(key, attribute);
        return new MapNodeBuilder(copy);
    }

    public Node complete() {
        return new MapNode(map);
    }
}