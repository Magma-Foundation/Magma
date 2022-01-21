package com.meti.app.compile.common.block;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.text.RootText;

import java.util.stream.Collectors;

public record BlockProcessor(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Type.Block)) {
            var builder = new StringBuilder().append("{");
            var children = node.apply(Attribute.Type.Children).asStreamOfNodes().collect(Collectors.toList());
            for (Node node1 : children) {
                builder.append(node1.apply(Attribute.Type.Value).asOutput().compute());
            }
            return new Some<>(new RootText(builder.append("}").toString()).toOutput());
        }
        return new None<>();
    }
}
