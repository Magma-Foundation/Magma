package com.meti.app.compile.common.condition;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.text.RootText;

public record ConditionRenderer(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Category.If)) {
            var arguments = node.apply(Attribute.Category.Arguments).asNode()
                    .apply(Attribute.Category.Value).asOutput();
            var value = this.node.apply(Attribute.Category.Value).asNode()
                    .apply(Attribute.Category.Value).asOutput();
            return new Some<>(new RootText("if(" + arguments.computeRaw() + ")" + value.computeRaw()).toOutput());
        }
        return new None<>();
    }
}
