package com.meti.compile.clang;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.compile.render.Renderer;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record UnaryRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Unary)) {
            var caller = node.apply(Attribute.Type.Caller).asNode().apply(Attribute.Type.Value).asText();
            var callee = node.apply(Attribute.Type.Arguments).asNode().apply(Attribute.Type.Value).asText();
            return new Some<>(caller.append(" ").append(callee));
        } else {
            return new None<>();
        }
    }
}
