package com.meti.node;

import java.util.List;
import java.util.Optional;

public interface Attribute {
    default Optional<List<?>> asListOfObjects() {
        return Optional.empty();
    }

    default Optional<Node> asNode() {
        return Optional.empty();
    }

    default Node asNodeUnsafe() {
        return asNode().orElseThrow(() -> {
            return new IllegalStateException("Not a node.");
        });
    }

    default Optional<String> asString() {
        return Optional.empty();
    }

    default Optional<List<Node>> asNodeList() {
        return Optional.empty();
    }

    default String asStringUnsafe() {
        return asString().orElseThrow(() -> {
            return new IllegalStateException("Not a string.");
        });
    }

    public interface Converter<T> {
        Attribute fromValue(T value);

        Optional<T> fromAttribute(Attribute value);

        T apply(T value);
    }
}
