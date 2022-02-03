package com.meti.app.compile.common.binary;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Output;

public record BinaryProcessor(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Category.Binary)) {
            try {
                var operator = node.apply(Attribute.Category.Operator).asNode().apply(Attribute.Category.Value).asOutput();
                var arguments = node.apply(Attribute.Category.Arguments)
                        .asStreamOfNodes()
                        .foldRight(List.<Node>createList(), List::add);

                var first = arguments.apply(0).apply(Attribute.Category.Value).asOutput();
                var second = arguments.apply(1).apply(Attribute.Category.Value).asOutput();

                var appendOutput = first.appendSlice(" ").appendOutput(operator);
                return new Some<>(appendOutput.appendSlice(" ").appendOutput(second));
            } catch (StreamException e) {
                return new None<>();
            }
        } else {
            return new None<>();
        }
    }
}
