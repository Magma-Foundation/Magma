package com.meti.node;

import java.util.List;
import java.util.Optional;

public interface Attribute {
    default Optional<List<MapNode>> asListOfNodes() {
        return Optional.empty();
    }

    default Optional<String> asString() {
        return Optional.empty();
    }

    default Optional<MapNode> asNode() {
        return Optional.empty();
    }
}
