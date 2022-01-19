package com.meti.app.compile.clang;

import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.AbstractProcessor;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Output;

final class StructureRenderer extends AbstractProcessor<Output> {
    public StructureRenderer(Node node) {
        super(node);
    }

    @Override
    protected boolean validate() {
        return node.is(Node.Type.Structure);
    }

    @Override
    protected Output createNode() throws CompileException {
        try {
            var name = renderName();
            var fields = renderFields();
            return name.prepend("struct ").appendSlice(fields).appendSlice(";");
        } catch (AttributeException | StreamException e) {
            throw new CompileException(e);
        }
    }

    private Output renderName() throws AttributeException {
        return node.apply(Attribute.Type.Name)
                .asInput()
                .toOutput();
    }

    private String renderFields() throws StreamException, AttributeException {
        return node.apply(Attribute.Type.Fields)
                .asStreamOfNodes1()
                .map(value -> value.apply(Attribute.Type.Value))
                .map(Attribute::asOutput)
                .map(Output::computeTrimmed)
                .map(value -> value + ";")
                .foldRight((current, next) -> current + next)
                .map(value -> "{" + value + "}")
                .orElse("{}");
    }
}
