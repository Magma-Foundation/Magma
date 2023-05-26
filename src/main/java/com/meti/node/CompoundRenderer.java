package com.meti.node;

import java.util.Optional;
import java.util.stream.Stream;

public abstract class CompoundRenderer implements Renderer {
    protected final Node node;

    public CompoundRenderer(Node node) {
        this.node = node;
    }

    protected abstract Stream<Renderer> streamRenderers(Node node);

    @Override
    public Optional<String> render() {
        return streamRenderers(node)
                .map(Renderer::render)
                .flatMap(Optional::stream)
                .findFirst();
    }
}
