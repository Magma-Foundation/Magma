package com.meti.app.compile.clang;

import com.meti.api.collect.stream.StreamException;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.text.StringOutput;

public record FunctionRenderer(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws CompileException {
        if (node.is(Node.Category.Abstraction) || node.is(Node.Category.Implementation)) {
            var identity = node.apply(Attribute.Type.Identity).asNode();
            var renderedIdentity = identity.apply(Attribute.Type.Value).asOutput();

            try {
                var parameters = node.apply(Attribute.Type.Parameters)
                        .asStreamOfNodes()
                        .map(value -> value.apply(Attribute.Type.Value))
                        .map(Attribute::asOutput)
                        .foldRight((current, next) -> current.appendSlice(",").appendOutput(next))
                        .map(value -> value.prepend("(").appendSlice(")"))
                        .orElse(new StringOutput("()"));

                var withIdentity = renderedIdentity.appendOutput(parameters);
                var withValue = attachValue(withIdentity);

                return new Some<>(withValue);
            } catch (StreamException e) {
                throw new CompileException(e);
            }
        }
        return new None<>();
    }

    private Output attachValue(Output withIdentity) throws AttributeException {
        if (node.is(Node.Category.Implementation)) {
            var value = node.apply(Attribute.Type.Value).asNode();
            var renderedValue = value.apply(Attribute.Type.Value).asOutput();
            return withIdentity.appendOutput(renderedValue);
        } else {
            return withIdentity.appendSlice(";");
        }
    }
}
