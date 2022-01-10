package com.meti.compile.clang;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.compile.render.Renderer;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public record FunctionRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Function)) {
            var identity = node.apply(Attribute.Type.Identity).asNode();
            var renderedIdentity = identity.apply(Attribute.Type.Value).asText();

            var parameters = node.apply(Attribute.Type.Parameters)
                    .asStreamOfNodes()
                    .collect(Collectors.toSet());
            var renderedParameterList = new ArrayList<String>();
            for (Node parameter : parameters) {
                renderedParameterList.add(parameter.apply(Attribute.Type.Value).asText().getTrimmedValue());
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
