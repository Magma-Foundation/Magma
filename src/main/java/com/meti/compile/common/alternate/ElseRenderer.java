package com.meti.compile.common.alternate;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.compile.render.Renderer;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record ElseRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Else)) {
            var value = node.apply(Attribute.Type.Value).asNode()
                    .apply(Attribute.Type.Value).asText();
            return new Some<>(value.prepend("else "));
        } else {
            return new None<>();
        }
    }
}
