package com.meti.compile.clang;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.compile.render.Renderer;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

record StructureRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Structure)) {
            var name = node.apply(Attribute.Type.Name).asText();
            return new Some<>(name.prepend("struct ").append("{};"));
        }
        return new None<>();
    }
}
