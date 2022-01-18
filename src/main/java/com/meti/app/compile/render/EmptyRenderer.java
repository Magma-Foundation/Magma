package com.meti.app.compile.render;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Text;

public record EmptyRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Empty)) {
            return new Some<>(new Text(""));
        } else {
            return new None<>();
        }
    }
}
