package com.meti.app.compile.common.variable;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.render.Renderer;

public record VariableRenderer(Node node) implements Renderer {
    @Override
    public Option<Output> render() throws AttributeException {
        if (node.is(Node.Type.Variable)) {
            return new Some<>(node.apply(Attribute.Type.Value).asText());
        } else {
            return new None<>();
        }
    }
}
