package com.meti.node;

import com.meti.node.function.ImplementationRenderer;

import java.util.stream.Stream;

public final class MagmaRenderer extends CompoundRenderer {

    public MagmaRenderer(Node node) {
        super(node);
    }

    @Override
    protected Stream<Renderer> streamRenderers(Node node) {
        return Stream.of(
                new ImplementationRenderer(node),
                new BlockRenderer(node),
                new ImportRenderer(node));
    }
}