package com.meti.node;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface Node {
    default Optional<Attribute> apply(String name) {
        return Optional.empty();
    }

    default Optional<Node> mapNodes(Function<Node, Optional<Node>> mapper) {
        return Optional.empty();
    }

    default Optional<Node> mapNodeLists(Function<List<Node>, Optional<List<Node>>> mapper) {
        return Optional.empty();
    }

    default boolean is(String id) {
        return false;
    }

    default Node replace(String name, Attribute attribute) {
        return this;
    }

    default Node ensure(String name, Attribute attribute) {
        return this;
    }

    default Node map(String name, Function<Attribute, Attribute> mapper) {
        return this;
    }

    default Node mapNodeList(String name, Function<List<Node>, List<Node>> mapper) {
        return map(name, attribute -> new NodeListAttribute(mapper.apply(attribute.asListOfNodes().orElseThrow())));
    }
}
