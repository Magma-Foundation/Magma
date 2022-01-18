package com.meti.app.compile.common.block;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.RootText;
import com.meti.app.compile.node.Text;
import com.meti.app.compile.render.Renderer;

import java.util.stream.Collectors;

public record BlockRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Block)) {
            var builder = new StringBuilder().append("{");
            var children = node.apply(Attribute.Type.Children).asStreamOfNodes().collect(Collectors.toList());
            for (Node node1 : children) {
                builder.append(node1.apply(Attribute.Type.Value).asText().computeTrimmed());
            }
            return new Some<>(new RootText(builder.append("}").toString()));
        }
        return new None<>();
    }
}
