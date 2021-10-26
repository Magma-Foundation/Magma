package com.meti.clang;

import com.meti.compile.AbstractRenderingStage;
import com.meti.compile.feature.BlockRenderer;
import com.meti.compile.feature.IntegerRenderer;
import com.meti.compile.feature.ReturnRenderer;
import com.meti.compile.node.Node;
import com.meti.compile.node.output.Output;

import java.util.stream.Stream;

public final class CRenderingStage extends AbstractRenderingStage {
    public CRenderingStage(Node node) {
        super(node);
    }

    @Override
    protected Stream<Processor<Output>> createProcessors() {
        return Stream.of(
                new BlockRenderer(node),
                new CImportRenderer(node),
                new ReturnRenderer(node),
                new IntegerRenderer(node));
    }
}
