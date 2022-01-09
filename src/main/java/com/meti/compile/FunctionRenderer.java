package com.meti.compile;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record FunctionRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Function)) {
            var identity = node.apply(Attribute.Type.Identity).asNode();
            var text = identity.apply(Attribute.Type.Value)
                    .asText();
            var value = node.apply(Attribute.Type.Value).asNode();
            var renderedValue = value.apply(Attribute.Type.Value).asText();
            return new Some<>(text.append("()").append(renderedValue));
        }
        return new None<>();
    }
}
