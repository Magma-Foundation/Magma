package com.meti.process;

import com.meti.node.Node;

public abstract class FilteredRenderer extends FilteredProcessor<String> {
    protected final Node value;
    private final Node.Type type;

    public FilteredRenderer(Node value, Node.Type type) {
        this.value = value;
        this.type = type;
    }

    @Override
    protected boolean isValid() {
        return value.is(type);
    }
}
