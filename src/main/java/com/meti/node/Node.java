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
