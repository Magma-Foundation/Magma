package com.meti.clang;

import com.meti.feature.Node;
import com.meti.output.Output;

public abstract class AbstractRenderer extends AbstractProcessor<Output> {
    protected final Node node;
    protected final Node.Type type;

    public AbstractRenderer(Node node, Node.Type type) {
        this.node = node;
        this.type = type;
    }

    @Override
    protected boolean isValid() {
        return node.is(type);
    }
}
