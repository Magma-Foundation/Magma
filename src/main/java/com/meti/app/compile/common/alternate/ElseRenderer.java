package com.meti.app.compile.common.alternate;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Text;
import com.meti.app.compile.render.Renderer;

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
