package com.meti.node;

import com.meti.api.option.None;
import com.meti.api.option.Option;

import java.util.List;
import java.util.Optional;

public interface Attribute {
    default Optional<List<String>> asListOfStrings() {
        return Optional.empty();
    }

    default Option<List<MapNode>> asListOfNodes() {
        return new None<>();
    }

    default Optional<String> asString() {
        return Optional.empty();
    }

    Option<MapNode> asNode();

    default boolean is(String key) {
        return false;
    }
}
