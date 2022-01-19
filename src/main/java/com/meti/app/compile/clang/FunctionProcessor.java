package com.meti.app.compile.clang;

import com.meti.api.collect.stream.StreamException;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Output;

import java.util.ArrayList;

public record FunctionProcessor(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws CompileException {
        if (node.is(Node.Type.Abstraction) || node.is(Node.Type.Implementation)) {
            var identity = node.apply(Attribute.Type.Identity).asNode();
            var renderedIdentity = identity.apply(Attribute.Type.Value).asOutput();

            ArrayList<String> parameters;
            try {
                parameters = node.apply(Attribute.Type.Parameters)
                        .asStreamOfNodes1()
                        .map(value -> value.apply(Attribute.Type.Value))
                        .map(Attribute::asOutput)
                        .map(Output::computeTrimmed)
                        .foldRight(new ArrayList<>(), (strings, s) -> {
                            strings.add(s);
                            return strings;
                        });
            } catch (StreamException e) {
                throw new CompileException(e);
            }

            parameters.sort(String::compareTo);
            var renderedParameters = String.join(",", parameters);
            var withIdentity = renderedIdentity.appendSlice("(" + renderedParameters + ")");
            var withValue = attachValue(withIdentity);
            return new Some<>(withValue);
        }
        return new None<>();
    }

    private Output attachValue(Output withIdentity) throws AttributeException {
        Output withValue;
        if (node.is(Node.Type.Implementation)) {
            var value = node.apply(Attribute.Type.Value).asNode();
            var renderedValue = value.apply(Attribute.Type.Value).asOutput();
            withValue = withIdentity.appendOutput(renderedValue);
        } else {
            withValue = withIdentity.appendSlice(";");
        }
        return withValue;
    }
}
