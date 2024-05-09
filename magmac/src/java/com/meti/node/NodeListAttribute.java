package com.meti.node;

import java.util.List;
import java.util.Optional;

public record NodeListAttribute(List<MapNode> nodes) implements Attribute {
    @Override
    public Optional<List<MapNode>> asListOfNodes() {
        return Optional.of(nodes);
    }
}
