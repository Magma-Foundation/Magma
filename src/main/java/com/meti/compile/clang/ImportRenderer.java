package com.meti.compile.clang;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.compile.render.Renderer;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.stream.Collectors;

public record ImportRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Import)) {
            var packaging = node.apply(Attribute.Type.Value).asPackaging();
            var parent = packaging.streamParent().collect(Collectors.joining("/"));
            var name = packaging.computeName();
            var formatted = parent.isEmpty() ? name : parent + "/" + name;
            return new Some<>(new Text("#include \"" + formatted + ".h\"\n"));
        } else {
            return new None<>();
        }
    }
}
