package com.meti.app.compile.common;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.render.Renderer;

public record LineRenderer(Node node) implements Renderer {
    @Override
    public Option<Output> render() throws AttributeException {
        if (node.is(Node.Type.Line)) {
            return new Some<>(node.apply(Attribute.Type.Value)
                    .asNode()
                    .apply(Attribute.Type.Value)
                    .asText()
                    .appendSlice(";"));
        } else {
            return new None<>();
        }
    }
}
