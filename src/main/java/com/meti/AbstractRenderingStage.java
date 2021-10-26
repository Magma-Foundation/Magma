package com.meti;

import com.meti.clang.Processor;
import com.meti.feature.Node;
import com.meti.output.Output;

public abstract class AbstractRenderingStage extends AbstractStage<Output, Processor<Output>> {
    protected final Node node;

    public AbstractRenderingStage(Node node) {
        this.node = node;
    }
}
