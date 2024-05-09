package com.meti.render;

import com.meti.node.MapNode;

import java.util.Optional;
import java.util.stream.Stream;

public record MagmaRenderer(MapNode node) implements Renderer {
    @Override
    public Optional<String> render() {
        return createRenderers()
                .map(Renderer::render)
                .flatMap(Optional::stream)
                .findFirst();
    }

    private Stream<Renderer> createRenderers() {
        return Stream.of(new ClassRenderer(node), new ImportRenderer(node));
    }
}