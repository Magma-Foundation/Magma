package com.meti.app.clang;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.output.Output;

public abstract class AbstractRenderer extends AbstractProcessor<Output> {
    protected final Node node;
    protected final Node.Type type;

    public AbstractRenderer(Node node, Node.Type type) {
        this.node = node;
        this.type = type;
    }

    @Override
    protected boolean validate() {
        return node.is(type);
    }
}
