package com.meti;

import java.util.HashMap;
import java.util.Map;

public class MapNode {
    private final Map<Attribute.Type, Attribute> attributes;
    private final NodeType type;

    public MapNode(String value, NodeType type) {
        this.type = type;
        this.attributes = new HashMap<>(Map.of(Attribute.Type.Value, new StringAttribute(value)));
    }

    public NodeType type() {
        return type;
    }

    public Attribute apply(Attribute.Type value) {
        return attributes.get(value);
    }
}
