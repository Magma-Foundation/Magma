package com.meti.node;

import com.meti.util.None;
import com.meti.util.Option;

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
}
