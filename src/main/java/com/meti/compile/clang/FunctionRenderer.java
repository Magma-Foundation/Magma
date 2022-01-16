package com.meti.compile.clang;

import com.meti.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.compile.render.Renderer;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record FunctionRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Implementation)) {
            var identity = node.apply(Attribute.Type.Identity).asNode();
            var renderedIdentity = identity.apply(Attribute.Type.Value).asText();

            try {
                var parameters = node.apply(Attribute.Type.Parameters)
                        .asStreamOfNodes()
                        .map(value -> value.apply(Attribute.Type.Value))
                        .map(Attribute::asText)
                        .map(Text::computeTrimmed)
                        .foldRight("", (current, next) -> current + next);
            } catch (StreamException e) {
                e.printStackTrace();
            }

            renderedParameterList.sort(String::compareTo);

            var renderedParameters = String.join(",", renderedParameterList);

            var value = node.apply(Attribute.Type.Value).asNode();
            var renderedValue = value.apply(Attribute.Type.Value).asText();
            return new Some<>(renderedIdentity.append("(" + renderedParameters + ")").append(renderedValue));
        }
        return new None<>();
    }
}
