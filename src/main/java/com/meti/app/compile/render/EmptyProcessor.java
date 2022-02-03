package com.meti.app.compile.render;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.text.RootText;

public record EmptyProcessor(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Category.Empty)) {
            return new Some<>(new RootText("").toOutput());
        } else {
            return new None<>();
        }
    }
}
