package com.meti.node;

import com.meti.util.Option;

public record MapNode(String type, NodeAttributes node) {
    public Option<Attribute> apply(String name) {
        return node.apply(name);
    }

    public boolean is(String type) {
        return this.type.equals(type);
    }

    public MapNode with(String key, Attribute attribute) {
        return new MapNode(type, node.with(key, attribute));
    }
}