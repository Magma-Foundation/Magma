package com.meti.app.compile.common.invoke;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.render.Processor;
import com.meti.app.compile.text.Output;

import java.util.ArrayList;
import java.util.stream.Collectors;

public record InvocationProcessor(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Type.Invocation)) {
            var caller = this.node.apply(Attribute.Type.Caller).asNode();
            var arguments = node.apply(Attribute.Type.Arguments).asStreamOfNodes().collect(Collectors.toList());
            var renderedArguments = new ArrayList<String>();
            for (Node argument : arguments) {
                renderedArguments.add(argument.apply(Attribute.Type.Value).asOutput().computeTrimmed());
            }
            return new Some<>(caller.apply(Attribute.Type.Value)
                    .asOutput()
                    .appendSlice("(")
                    .appendSlice(String.join(",", renderedArguments))
                    .appendSlice(")"));
        } else {
            return new None<>();
        }
    }
}
