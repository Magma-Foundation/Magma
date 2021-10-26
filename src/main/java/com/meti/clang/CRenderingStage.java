package com.meti.clang;

import com.meti.AbstractRenderingStage;
import com.meti.feature.IntegerRenderer;
import com.meti.feature.Node;
import com.meti.feature.ReturnRenderer;
import com.meti.output.Output;

import java.util.stream.Stream;

public final class CRenderingStage extends AbstractRenderingStage {
    public CRenderingStage(Node node) {
        super(node);
    }

    @Override
    protected Stream<Processor<Output>> createProcessors() {
        return Stream.of(
                new CImportRenderer(node),
                new ReturnRenderer(node),
                new IntegerRenderer(node));
    }
}
