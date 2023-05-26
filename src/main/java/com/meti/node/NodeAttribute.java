package com.meti.node;

import java.util.Optional;

public class NodeAttribute implements Attribute {
    private final Node value;

    @Override
    public Optional<Node> asNode() {
        return Optional.of(value);
    }

    public NodeAttribute(Node value) {
        this.value = value;
    }
}
