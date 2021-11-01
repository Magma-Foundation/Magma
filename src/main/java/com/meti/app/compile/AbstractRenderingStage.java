package com.meti.app.compile;

import com.meti.app.clang.Processor;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.output.Output;

public abstract class AbstractRenderingStage extends AbstractStage<Output, Processor<Output>> {
    protected final Node node;

    @Override
    protected CompileException invalidate() {
        return new CompileException("Failed to render node: " + node);
    }

    public AbstractRenderingStage(Node node) {
        this.node = node;
    }
}
