package com.meti.app.compile.clang;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Output;

public record UnaryProcessor(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Category.Unary)) {
            var caller = node.apply(Attribute.Type.Caller).asNode().apply(Attribute.Type.Value).asOutput();
            var callee = node.apply(Attribute.Type.Arguments).asNode().apply(Attribute.Type.Value).asOutput();
            return new Some<>(caller.appendSlice(" ").appendOutput(callee));
        } else {
            return new None<>();
        }
    }
}
