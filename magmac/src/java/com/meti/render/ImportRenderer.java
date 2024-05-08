package com.meti.render;

import com.meti.MapNode;

import java.util.Optional;

public record ImportRenderer(MapNode mapNode) implements Renderer {
    @Override
    public Optional<String> render() {
        if (!mapNode().type().equals("import")) return Optional.empty();

        var child1 = mapNode().node().get("child");
        var parent1 = mapNode().node().get("parent");

        return Optional.of("import { " + child1 + " } from " + parent1 + ";\n");
    }
}