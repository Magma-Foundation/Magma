package com.meti.node;

import java.util.stream.Stream;

public class JavaRenderer extends CompoundRenderer {
    public JavaRenderer(Node node) {
        super(node);
    }

    @Override
    protected Stream<Renderer> streamRenderers(Node node) {
        return Stream.of(
                new BlockRenderer(node),
                new JavaClassRenderer(node));
    }
}
