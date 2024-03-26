package com.meti.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NodeListAttribute implements Attribute {
    private final List<Node> nodes;

    public NodeListAttribute(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public Attribute merge(Attribute value) {
        return value.asListOfNodes().map(otherList -> {
            var copy = new ArrayList<>(nodes);
            copy.addAll(otherList);
            return new NodeListAttribute(copy);
        }).orElse(this);
    }

    @Override
    public Optional<List<Node>> asListOfNodes() {
        return Optional.of(nodes);
    }
}
