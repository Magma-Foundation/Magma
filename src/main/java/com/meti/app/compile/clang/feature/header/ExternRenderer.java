package com.meti.app.compile.clang.feature.header;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.text.RootText;

public record ExternRenderer(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Category.Extern)) {
            return new Some<>(new RootText("#include <" + node.apply(Attribute.Category.Value)
                    .asInput()
                    .toOutput()
                    .compute() + ".h>\n")
                    .toOutput());
        } else {
            return new None<>();
        }
    }
}
