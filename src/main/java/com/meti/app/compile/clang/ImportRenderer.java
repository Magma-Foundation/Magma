package com.meti.app.compile.clang;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.RootText;
import com.meti.app.compile.node.Text;
import com.meti.app.compile.render.Renderer;

import java.util.stream.Collectors;

public record ImportRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Import)) {
            var packaging = node.apply(Attribute.Type.Value).asPackaging();
            var parent = packaging.streamParent().collect(Collectors.joining("/"));
            var name = packaging.computeName();
            var formatted = parent.isEmpty() ? name : parent + "/" + name;
            return new Some<>(new RootText("#include \"" + formatted + ".h\"\n"));
        } else {
            return new None<>();
        }
    }
}
