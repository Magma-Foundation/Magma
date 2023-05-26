package com.meti.node;

import java.util.Optional;
import java.util.stream.Stream;

public interface Node {
    default boolean is(Object key) {
        return false;
    }

    default Optional<Attribute> apply(Object key) {
        return Optional.empty();
    }

    default Attribute applyUnsafe(Object key) {
        return apply(key).orElseThrow(() -> {
            var format = "'%s' is not a valid property for types of '%s'.";
            var message = format.formatted(key, getClass());
            throw new IllegalArgumentException(message);
        });
    }

    default Optional<Node> with(Object key, Attribute value) {
        return Optional.empty();
    }

    default <T> Optional<Node> map(Object key, Attribute.Converter<T> converter) {
        return apply(key).flatMap(previous -> converter.fromAttribute(previous).flatMap(unwrapped -> {
            var result = converter.apply(unwrapped);
            var next = converter.fromValue(result);
            return with(key, next);
        }));
    }

    default Stream<Object> stream(Group group) {
        return Stream.empty();
    }

    public enum Group {
        Node,
        NodeList
    }
}
