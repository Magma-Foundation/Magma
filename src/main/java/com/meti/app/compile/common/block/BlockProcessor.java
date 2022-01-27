package com.meti.app.compile.common.block;

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

public record BlockProcessor(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Type.Block)) {
            try {
                var output = node.apply(Attribute.Type.Children)
                        .asStreamOfNodes()
                        .map(child -> child.apply(Attribute.Type.Value))
                        .map(Attribute::asOutput)
                        .foldRight(Output::appendOutput)
                        .map(value -> value.prepend("{").appendSlice("}"))
                        .orElse(new StringOutput("{}"));
                return new Some<>(output);
            } catch (StreamException e) {
                return new None<>();
            }
        }
        return new None<>();
    }
}
