package com.meti.compile.common.integer;

import com.meti.compile.render.Renderer;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record IntegerRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Integer)) {
            return new Some<>(new Text(String.valueOf(node.apply(Attribute.Type.Value).asInteger())));
        } else {
            return new None<>();
        }
    }
}
