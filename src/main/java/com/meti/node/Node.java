package com.meti.node;

import java.util.Optional;

public interface Node {
    default boolean is(Object key) {
        return false;
    }

    default Optional<Attribute> apply(Object key) {
        return Optional.empty();
    }

    default Optional<Node> with(Object key, Attribute value) {
        return Optional.empty();
    }

    default <T> Optional<Node> map(Object key, Attribute.Converter<T> converter) {
        return apply(key).flatMap(previous -> {
            var value = converter.fromAttribute(previous);
            var result = converter.apply(value);
            var next = converter.fromValue(result);
            return with(key, next);
        });
    }

    String render();
}
