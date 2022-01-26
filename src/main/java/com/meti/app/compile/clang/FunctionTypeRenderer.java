package com.meti.app.compile.clang;

import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.text.StringOutput;

public class FunctionTypeRenderer extends AbstractTypeRenderer {
    public FunctionTypeRenderer(Input name, Node type) {
        super(name, type, Node.Type.Function);
    }

    @Override
    protected Output processValid() throws CompileException {
        try {
            var returns = type.apply(Attribute.Type.Type).asOutput();
            var parameters = type.apply(Attribute.Type.Parameters)
                    .asStreamOfNodes1()
                    .map(value -> value.apply(Attribute.Type.Value))
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
