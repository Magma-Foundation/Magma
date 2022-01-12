package com.meti.compile.common.invoke;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.compile.render.Renderer;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

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
