package com.meti.node;

import java.util.List;
import java.util.Optional;

public interface Attribute {
    Optional<Node> asNode();

    Optional<String> asString();

    Optional<List<Node>> asNodeList();

    public interface Converter<T> {
        Attribute fromValue(T value);

        Optional<T> fromAttribute(Attribute value);

        T apply(T value);
    }
}
