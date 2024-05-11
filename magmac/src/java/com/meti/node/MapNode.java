package com.meti.node;

import com.meti.Tuple;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

import java.util.function.BiFunction;

public record MapNode(String type, NodeAttributes attributes) {

    public Option<Attribute> apply(String name) {
        return attributes.apply(name);
    }

    public boolean is(String type) {
        return this.type.equals(type);
    }

    public MapNode with(String key, Attribute attribute) {
        return new MapNode(type, attributes.with(key, attribute));
    }

    public <T, S> Tuple<Option<MapNode>, S> map(
            AttributeFactory<T> factory,
            BiFunction<S, T, Option<Tuple<Option<T>, S>>> mapper,
            S state) {
        return attributes.filter(factory)
                .stream()
                .reduce(new Tuple<>(new Some<>(this), state),
                        (current, key) -> {
                            var map = current.left()
                                    .flatMap(inner -> inner.map(key, factory, mapper, current.right()));
                            return map.orElse(current);
                        },
                        (node, node2) -> node2);
    }

    public <T, S> Option<Tuple<Option<MapNode>, S>> map(
            String key,
            AttributeFactory<T> factory,
            BiFunction<S, T, Option<Tuple<Option<T>, S>>> mapper,
            S state) {
        return apply(key)
                .flatMap(factory::fromAttribute)
                .flatMap(value -> mapper.apply(state, value))
                .map((Tuple<Option<T>, S> value1) -> value1.mapLeft((Option<T> value2) -> value2.map(factory::toAttribute)))
                .map(attribute -> attribute.mapLeft(left0 -> left0.map(left -> with(key, left))));
    }

    public MapNode rename(String name) {
        return new MapNode(name, attributes);
    }
}