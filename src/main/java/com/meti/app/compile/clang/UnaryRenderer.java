package com.meti.app.compile.clang;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.render.Renderer;

public record UnaryRenderer(Node node) implements Renderer {
    @Override
    public Option<Output> render() throws AttributeException {
        if (node.is(Node.Type.Unary)) {
            var caller = node.apply(Attribute.Type.Caller).asNode().apply(Attribute.Type.Value).asText();
            var callee = node.apply(Attribute.Type.Arguments).asNode().apply(Attribute.Type.Value).asText();
            return new Some<>(caller.appendSlice(" ").appendOutput(callee));
        } else {
            return new None<>();
        }
    }
}
