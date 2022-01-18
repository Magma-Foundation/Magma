package com.meti.app.compile.common.binary;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Text;
import com.meti.app.compile.render.Renderer;

import java.util.stream.Collectors;

public record BinaryRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Binary)) {
            var operator = node.apply(Attribute.Type.Operator).asNode().apply(Attribute.Type.Value).asText();
            var arguments = node.apply(Attribute.Type.Arguments).asStreamOfNodes().collect(Collectors.toList());
            var first = arguments.get(0).apply(Attribute.Type.Value).asText();
            var second = arguments.get(1).apply(Attribute.Type.Value).asText();
            return new Some<>(first.appendSlice(" ").appendText(operator).appendSlice(" ").appendText(second));
        } else {
            return new None<>();
        }
    }
}
