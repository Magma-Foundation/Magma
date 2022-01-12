package com.meti.compile.common.string;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.compile.render.Renderer;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record StringRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.String)) {
            return new Some<>(node.apply(Attribute.Type.Value).asText().prepend("\"").append("\""));
        } else {
            return new None<>();
        }
    }
}
