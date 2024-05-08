package com.meti;

import java.util.Optional;

public record ClassRenderer(MapNode mapNode) implements Renderer {
    @Override
    public Optional<String> render() {
        if (!mapNode().type().equals("class")) return Optional.empty();

        var name = mapNode().node().get("name");
        var content = mapNode().node().get("content");

        return Optional.of("class def " + name + "() => " + content);
    }
}