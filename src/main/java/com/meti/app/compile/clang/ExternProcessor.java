package com.meti.app.compile.clang;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.render.Processor;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.text.RootText;

public record ExternProcessor(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Type.Extern)) {
            return new Some<>(new RootText("#include <" + node.apply(Attribute.Type.Value)
                    .asOutput().computeTrimmed() + ".h>\n"));
        } else {
            return new None<>();
        }
    }
}
