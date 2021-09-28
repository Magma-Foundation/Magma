package com.meti;

import java.util.Collection;
import java.util.stream.Stream;

public interface Node {
    default Node apply(Type type) {
        throw new UnsupportedOperationException("No children present.");
    }

    Group group();

    default Stream<Node> streamChildren() {
        return Stream.empty();
    }

    default String getValue() {
        throw new UnsupportedOperationException("No value was found in this node.");
    }

    String render();

    default Stream<Type> streamNodes() {
        return Stream.empty();
    }

    default Node withNode(Node node) {
        return this;
    }

    enum Group {
        Function, Return, Integer, Empty, Block, Declaration, Content
    }

    enum Type {
        Value, Body
    }

    default Node withNodes(Collection<Node> nodes) {
        return this;
    }
}
