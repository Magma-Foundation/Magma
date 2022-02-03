package com.meti.app.compile.common.invoke;

import com.meti.api.collect.stream.StreamException;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.text.StringOutput;

public record InvocationProcessor(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Category.Invocation)) {
            try {
                var caller = this.node.apply(Attribute.Type.Caller).asNode();
                var renderedArguments = node.apply(Attribute.Type.Arguments)
                        .asStreamOfNodes()
                        .map(value -> value.apply(Attribute.Type.Value))
                        .map(Attribute::asOutput)
                        .foldRight((current, next) -> current.appendSlice(",").appendOutput(next))
                        .map(value -> value.prepend("(").appendSlice(")"))
                        .orElse(new StringOutput("()"));
                var output = caller.apply(Attribute.Type.Value).asOutput().appendOutput(renderedArguments);
                return new Some<>(output);
            } catch (StreamException e) {
                return new None<>();
            }
        } else {
            return new None<>();
        }
    }
}
