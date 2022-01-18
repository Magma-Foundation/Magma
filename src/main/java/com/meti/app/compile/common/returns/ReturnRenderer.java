package com.meti.app.compile.common.returns;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Text;
import com.meti.app.compile.render.Renderer;

public record ReturnRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Return)) {
            var child = node.apply(Attribute.Type.Value).asNode();
            var renderedChild = child.apply(Attribute.Type.Value).asText();
            return new Some<>(renderedChild.prepend("return ").appendSlice(";"));
        }
        return new None<>();
    }
}
