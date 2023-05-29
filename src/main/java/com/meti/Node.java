package com.meti;

import java.util.List;
import java.util.Optional;

public interface Node {
    String render();

    default boolean hasNamespace(List<String> node) {
        return false;
    }

    default Optional<Node> get(List<String> values) {
        return Optional.empty();
    }
}
