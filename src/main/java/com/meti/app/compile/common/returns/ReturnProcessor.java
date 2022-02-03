package com.meti.app.compile.common.returns;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Output;

public record ReturnProcessor(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Category.Return)) {
            var child = node.apply(Attribute.Category.Value).asNode();
            var renderedChild = child.apply(Attribute.Category.Value).asOutput();
            return new Some<>(renderedChild.prepend("return ").appendSlice(";"));
        }
        return new None<>();
    }
}
