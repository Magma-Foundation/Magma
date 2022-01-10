package com.meti.compile.common.variable;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.compile.render.Renderer;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record VariableRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Variable)) {
            return new Some<>(node.apply(Attribute.Type.Value).asText());
        } else {
            return new None<>();
        }
    }
}
