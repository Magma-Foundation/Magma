package com.meti.compile.clang;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.compile.render.Renderer;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record DeclarationRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Declaration)) {
            var text = node.apply(Attribute.Type.Identity)
                    .asNode()
                    .apply(Attribute.Type.Value)
                    .asText();
            return new Some<>(text.append(";"));
        } else {
            return new None<>();
        }
    }
}
