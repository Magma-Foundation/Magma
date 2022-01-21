package com.meti.app.compile.common.integer;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.text.RootText;

public record IntegerProcessor(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Type.Integer)) {
            return new Some<>(new RootText(String.valueOf(node.apply(Attribute.Type.Value).asInteger())).toOutput());
        } else {
            return new None<>();
        }
    }
}
