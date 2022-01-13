package com.meti.compile;

import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.compile.render.RenderException;
import com.meti.compile.render.Renderer;
import com.meti.option.Option;
import com.meti.option.Some;

public abstract class AbstractRenderer implements Renderer {
    protected final Node node;

    public AbstractRenderer(Node node) {
        this.node = node;
    }

    protected abstract boolean filter(Node value);

    @Override
    public Option<Text> render() throws RenderException {
        return new Some<>(node)
                .filter(this::filter)
                .map(this::renderImpl);
    }

    protected abstract Text renderImpl(Node node) throws RenderException;
}
