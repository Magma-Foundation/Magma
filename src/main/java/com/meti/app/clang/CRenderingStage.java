package com.meti.app.clang;

import com.meti.app.compile.AbstractRenderingStage;
import com.meti.app.compile.feature.BlockRenderer;
import com.meti.app.compile.feature.IntegerRenderer;
import com.meti.app.compile.feature.ReturnRenderer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.output.Output;

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
