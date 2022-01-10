package com.meti.compile.render;

import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

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
