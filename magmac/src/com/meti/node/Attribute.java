package com.meti.node;

import java.util.List;
import java.util.Optional;

public interface Attribute {
    default Optional<String> asString() {
        return Optional.empty();
    }

    default Optional<Integer> asInt() {
        return Optional.empty();
    }

    default Optional<Node> asNode() {
        return Optional.empty();
    }

    default Optional<List<Node>> asListOfNodes() {
        return Optional.empty();
    }

    default Optional<List<String>> asListOfStrings() {
        return Optional.empty();
    }
}
