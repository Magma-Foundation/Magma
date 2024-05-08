package com.meti;

import java.util.Optional;

public record PrimitiveRenderer(MapNode node) implements Renderer {

    @Override
    public Option<String> render() {
        return new Some<>(node.values().get("value"));
    }
}