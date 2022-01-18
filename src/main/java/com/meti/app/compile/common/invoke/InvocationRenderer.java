package com.meti.app.compile.common.invoke;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Text;
import com.meti.app.compile.render.Renderer;

import java.util.ArrayList;
import java.util.stream.Collectors;

public record InvocationRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Invocation)) {
            var caller = this.node.apply(Attribute.Type.Caller).asNode();
            var arguments = node.apply(Attribute.Type.Arguments).asStreamOfNodes().collect(Collectors.toList());
            var renderedArguments = new ArrayList<String>();
            for (Node argument : arguments) {
                renderedArguments.add(argument.apply(Attribute.Type.Value).asText().computeTrimmed());
            }
            return new Some<>(caller.apply(Attribute.Type.Value)
                    .asText()
                    .append("(")
                    .append(String.join(",", renderedArguments))
                    .append(")"));
        } else {
            return new None<>();
        }
    }
}
