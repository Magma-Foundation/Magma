package com.meti;

public record ImportRenderer(MapNode mapNode) implements Renderer {
    @Override
    public Option<String> render() {
        return new Some<>("import { %s } from %s;\n".formatted(mapNode().values().get("child"), mapNode().values().get("parent")));
    }
}