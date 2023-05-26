package com.meti.node;

import java.util.stream.Stream;

public final class MagmaRenderer extends CompoundRenderer {

    public MagmaRenderer(Node node) {
        super(node);
    }

    @Override
    protected Stream<Renderer> streamRenderers(Node node) {
        return Stream.of(new ImportRenderer(node));
    }

}