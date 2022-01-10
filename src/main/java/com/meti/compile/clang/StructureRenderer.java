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

record StructureRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Structure)) {
            var name = node.apply(Attribute.Type.Name).asText();
            var fields = node.apply(Attribute.Type.Fields).asStreamOfNodes().collect(Collectors.toList());
            var builder = new StringBuilder();
            for (Node field : fields) {
                builder.append(field.apply(Attribute.Type.Value).asText().compute()).append(";");
            }
            return new Some<>(name.prepend("struct ")
                    .append("{")
                    .append(builder.toString())
                    .append("}")
                    .append(";"));
        }
        return new None<>();
    }
}
