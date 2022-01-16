package com.meti.compile.common.binary;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.compile.render.Renderer;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.stream.Collectors;

public record BinaryRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Binary)) {
            var operator = node.apply(Attribute.Type.Operator).asNode().apply(Attribute.Type.Value).asText();
            var arguments = node.apply(Attribute.Type.Arguments).asStreamOfNodes().collect(Collectors.toList());
            var first = arguments.get(0).apply(Attribute.Type.Value).asText();
            var second = arguments.get(1).apply(Attribute.Type.Value).asText();
            return new Some<>(first.append(" ").append(operator).append(" ").append(second));
        } else {
            return new None<>();
        }
    }
}
