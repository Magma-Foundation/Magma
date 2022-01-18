package com.meti.app.compile.common.alternate;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Output;

public record ElseProcessor(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Type.Else)) {
            var value = node.apply(Attribute.Type.Value).asNode()
                    .apply(Attribute.Type.Value).asOutput();
            return new Some<>(value.prepend("else "));
        } else {
            return new None<>();
        }
    }
}
