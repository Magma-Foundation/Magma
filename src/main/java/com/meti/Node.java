package com.meti;

import java.util.Collection;
import java.util.stream.Stream;

public interface Node {
    default String getValue() {
        throw new UnsupportedOperationException("No value was found in this node.");
    }

    String render();

    default Stream<Node> streamNodes() {
        return Stream.empty();
    }

    default Node withNodes(Collection<Node> nodes) {
        return this;
    }
}
