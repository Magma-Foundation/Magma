package com.meti.compile.common.block;

import com.meti.compile.render.Renderer;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.stream.Collectors;

public record BlockRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Block)) {
            var builder = new StringBuilder().append("{");
            var children = node.apply(Attribute.Type.Children).asStreamOfNodes().collect(Collectors.toList());
            for (Node node1 : children) {
                builder.append(node1.apply(Attribute.Type.Value).asText().compute());
            }
            return new Some<>(new Text(builder.append("}").toString()));
        }
        return new None<>();
    }
}
