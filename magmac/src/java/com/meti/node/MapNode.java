package com.meti.node;

import com.meti.Tuple;
import com.meti.util.Option;

import java.util.function.BiFunction;

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

    public <T, S> Tuple<MapNode, S> map(
            AttributeFactory<T> factory,
            BiFunction<S, T, Option<Tuple<T, S>>> mapper,
            S state) {
        return node.filter(factory)
                .stream()
                .reduce(new Tuple<>(this, state), (current, key) -> current.left().map(key, factory, mapper, current.right()).orElse(current), (node, node2) -> node2);
    }

    public <T, S> Option<Tuple<MapNode, S>> map(
            String key,
            AttributeFactory<T> factory,
            BiFunction<S, T, Option<Tuple<T, S>>> mapper,
            S state) {
        return apply(key)
                .flatMap(factory::fromAttribute)
                .flatMap(value -> mapper.apply(state, value))
                .map((Tuple<T, S> value1) -> value1.mapLeft(factory::toAttribute))
                .map(attribute -> attribute.mapLeft(left -> with(key, left)));
    }
}