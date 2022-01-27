package com.meti.app.compile.clang;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Output;

public record DefinitionRenderer(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Type.Declaration)) {
            var text = node.apply(Attribute.Type.Identity)
                    .asNode()
                    .apply(Attribute.Type.Value)
                    .asOutput();
            return new Some<>(text.appendSlice(";"));
        } else {
            return new None<>();
        }
    }
}
