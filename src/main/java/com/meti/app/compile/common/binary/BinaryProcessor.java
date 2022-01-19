package com.meti.app.compile.common.binary;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Output;

import java.util.stream.Collectors;

public record BinaryProcessor(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Type.Binary)) {
            var operator = node.apply(Attribute.Type.Operator).asNode().apply(Attribute.Type.Value).asOutput();
            var arguments = node.apply(Attribute.Type.Arguments).asStreamOfNodes().collect(Collectors.toList());
            var first = arguments.get(0).apply(Attribute.Type.Value).asOutput();
            var second = arguments.get(1).apply(Attribute.Type.Value).asOutput();
            return new Some<>(first.appendSlice(" ").appendOutput(operator).appendSlice(" ").appendOutput(second));
        } else {
            return new None<>();
        }
    }
}
