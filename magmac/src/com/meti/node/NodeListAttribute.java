package com.meti.node;

import java.util.List;
import java.util.Optional;

public class NodeListAttribute implements Attribute {
    private final List<Node> nodes;

    public NodeListAttribute(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public Optional<List<Node>> asListOfNodes() {
        return Optional.of(nodes);
    }
}
