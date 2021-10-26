package com.meti.clang;

import com.meti.feature.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;
import com.meti.output.Output;

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
