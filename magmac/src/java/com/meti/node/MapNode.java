package com.meti.node;

import com.meti.util.Option;

import java.util.function.Function;

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

    public <T> Option<MapNode> map(String key, AttributeFactory<T> factory, Function<T, Option<T>> mapper) {
        return apply(key)
                .flatMap(factory::fromAttribute)
                .flatMap(mapper)
                .map(factory::toAttribute)
                .map(attribute -> with(key, attribute));
    }
}