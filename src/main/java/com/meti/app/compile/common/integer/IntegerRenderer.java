package com.meti.app.compile.common.integer;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Text;
import com.meti.app.compile.render.Renderer;

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
