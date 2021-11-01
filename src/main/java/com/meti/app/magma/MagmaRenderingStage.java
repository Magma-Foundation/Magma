package com.meti.app.magma;

import com.meti.api.ArrayStream;
import com.meti.api.Stream;
import com.meti.app.clang.Processor;
import com.meti.app.compile.AbstractRenderingStage;
import com.meti.app.compile.feature.IntegerRenderer;
import com.meti.app.compile.feature.ReturnRenderer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.output.Output;

public class MagmaRenderingStage extends AbstractRenderingStage {
    public MagmaRenderingStage(Node node) {
        super(node);
    }

    @Override
    protected Stream<Processor<Output>> createProcessors() {
        return new ArrayStream<>(
                new MagmaImportRenderer(node),
                new ReturnRenderer(node),
                new IntegerRenderer(node));
    }
}
