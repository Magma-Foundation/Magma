package com.meti;

import java.util.Optional;

public record ImportRenderer(MapNode mapNode) implements Renderer {
    @Override
    public Optional<String> render() {
        return Optional.of("import { %s } from %s;\n".formatted(mapNode().values().get("child"), mapNode().values().get("parent")));
    }
}