package com.meti.magma;

import com.meti.AbstractRenderingStage;
import com.meti.clang.AbstractRenderer;
import com.meti.feature.IntegerRenderer;
import com.meti.feature.Node;
import com.meti.feature.ReturnRenderer;

import java.util.stream.Stream;

public class MagmaRenderingStage extends AbstractRenderingStage {
    public MagmaRenderingStage(Node node) {
        super(node);
    }

    @Override
    protected Stream<AbstractRenderer> createRenderers() {
        return Stream.of(
                new MagmaImportRenderer(node),
                new ReturnRenderer(node),
                new IntegerRenderer(node));
    }
}
