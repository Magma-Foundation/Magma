package com.meti.app.compile.clang.feature.scope;

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
        if (node.is(Node.Category.Declaration)) {
            var text = node.apply(Attribute.Category.Identity)
                    .asNode()
                    .apply(Attribute.Category.Value)
                    .asOutput();
            return new Some<>(text.appendSlice(";"));
        } else {
            return new None<>();
        }
    }
}
