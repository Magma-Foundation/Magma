package com.meti;

import java.util.Optional;

public record PrimitiveRenderer(MapNode node) implements Renderer {

    @Override
    public Optional<String> render() {
        return Optional.of(node.values().get("value"));
    }
}