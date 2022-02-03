package com.meti.app.compile.clang.feature.function;

import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.clang.stage.OutputRenderer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.text.StringOutput;

public class FunctionTypeRenderer extends OutputRenderer {
    public FunctionTypeRenderer(Node identity) {
        super(identity, Node.Category.Function);
    }

    @Override
    protected Output processImpl() throws CompileException {
        try {
            var name = identity.apply(Attribute.Category.Name).asInput();
            var returns = identity.apply(Attribute.Category.Type).asOutput();
            var parameters = identity.apply(Attribute.Category.Parameters)
                    .asStreamOfNodes()
                    .map(value -> value.apply(Attribute.Category.Value))
                    .map(Attribute::asOutput)
                    .foldRight((current, next) -> current.appendSlice(",").appendOutput(next))
                    .map(value -> value.prepend("(").appendSlice(")"))
                    .orElse(new StringOutput("()"));
            return returns.appendSlice(" (*")
                    .appendOutput(name.toOutput())
                    .appendSlice(")")
                    .appendOutput(parameters);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }
}
