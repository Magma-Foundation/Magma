package com.meti.node;

import java.util.List;
import java.util.Optional;

public interface Attribute {
    default Optional<Node> asNode() {
        return Optional.empty();
    }

    default Optional<String> asString() {
        return Optional.empty();
    }

    default Optional<List<Node>> asNodeList() {
        return Optional.empty();
    }

    public interface Converter<T> {
        Attribute fromValue(T value);

        Optional<T> fromAttribute(Attribute value);

        T apply(T value);
    }
}
