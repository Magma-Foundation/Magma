package com.meti.app.compile.render;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Output;

public abstract class AbstractRenderer implements Renderer {
    protected final Node node;

    public AbstractRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Option<Output> render() throws AttributeException {
        if (validate()) {
            return new Some<>(createNode());
        } else {
            return new None<>();
        }
    }

    protected abstract boolean validate();

    protected abstract Output createNode() throws AttributeException;
}
