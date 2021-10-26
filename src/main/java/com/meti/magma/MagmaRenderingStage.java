package com.meti.magma;

import com.meti.clang.Processor;
import com.meti.compile.AbstractRenderingStage;
import com.meti.compile.Node;
import com.meti.compile.feature.IntegerRenderer;
import com.meti.compile.feature.ReturnRenderer;
import com.meti.compile.output.Output;

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
