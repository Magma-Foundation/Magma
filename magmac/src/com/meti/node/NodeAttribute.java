package com.meti.node;

import java.util.Optional;

public class NodeAttribute implements Attribute {
    public static String Id = "node";
    private final Node value;

    public NodeAttribute(Node value) {
        this.value = value;
    }

    @Override
    public boolean is(String id) {
        return Id.equals(id);
    }

    @Override
    public Optional<Node> asNode() {
        return Optional.of(value);
    }
}
