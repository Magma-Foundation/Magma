package com.meti.clang;

import com.meti.None;
import com.meti.Option;
import com.meti.Output;
import com.meti.Some;
import com.meti.feature.Node;

public abstract class AbstractRenderer implements Renderer {
    protected final Node node;
    protected final Node.Type type;

    public AbstractRenderer(Node node, Node.Type type) {
        this.node = node;
        this.type = type;
    }

    @Override
    public Option<Output> render() {
        if (node.is(type)) {
            Output output = renderDefined();
            return new Some<>(output);
        }
        return new None<>();
    }

    protected abstract Output renderDefined();
}
