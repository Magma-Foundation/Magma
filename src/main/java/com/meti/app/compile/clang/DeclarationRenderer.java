package com.meti.app.compile.clang;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Text;
import com.meti.app.compile.render.Renderer;

public record DeclarationRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Declaration)) {
            var text = node.apply(Attribute.Type.Identity)
                    .asNode()
                    .apply(Attribute.Type.Value)
                    .asText();
            return new Some<>(text.appendSlice(";"));
        } else {
            return new None<>();
        }
    }
}
