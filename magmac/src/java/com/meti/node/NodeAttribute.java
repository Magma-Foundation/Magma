package com.meti.node;

import java.util.Optional;

public record NodeAttribute(MapNode value) implements Attribute{
    @Override
    public Optional<MapNode> asNode() {
        return Optional.of(value);
    }
}
