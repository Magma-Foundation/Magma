package com.meti.magma;

import com.meti.AbstractRenderingStage;
import com.meti.clang.Processor;
import com.meti.feature.IntegerRenderer;
import com.meti.feature.Node;
import com.meti.feature.ReturnRenderer;
import com.meti.output.Output;

import java.util.stream.Stream;

public class MagmaRenderingStage extends AbstractRenderingStage {
    public MagmaRenderingStage(Node node) {
        super(node);
    }

    @Override
    protected Stream<Processor<Output>> createProcessors() {
        return Stream.of(
                new MagmaImportRenderer(node),
                new ReturnRenderer(node),
                new IntegerRenderer(node));
    }
}
