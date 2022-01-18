package com.meti.app.compile.render;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.text.RootText;

public record EmptyProcessor(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Type.Empty)) {
            return new Some<>(new RootText(""));
        } else {
            return new None<>();
        }
    }
}
