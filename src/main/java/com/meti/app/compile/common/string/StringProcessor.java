package com.meti.app.compile.common.string;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Output;

public record StringProcessor(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Type.String)) {
            return new Some<>(node.apply(Attribute.Type.Value).asOutput());
        } else {
            return new None<>();
        }
    }
}
