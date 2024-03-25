package com.meti;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface Node {
    default Optional<String> findValue() {
        return Optional.empty();
    }

    default Optional<Integer> findIndent() {
        return Optional.empty();
    }

    default Optional<String> render() {
        return Optional.empty();
    }

    default Optional<Node> mapNodes(Function<Node, Optional<Node>> mapper) {
        return Optional.empty();
    }

    default Optional<Node> mapNodeLists(Function<List<Node>, Optional<List<Node>>> mapper) {
        return Optional.empty();
    }
}
