package com.meti.app.compile.clang;

import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.AbstractProcessor;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Output;

final class StructureRenderer extends AbstractProcessor<Node, Output> {
    public StructureRenderer(Node node) {
        super(node);
    }

    private String renderFields() throws StreamException, AttributeException {
        return input.apply(Attribute.Type.Fields)
                .asStreamOfNodes()
                .map(value -> value.apply(Attribute.Type.Value))
                .map(Attribute::asOutput)
                .map(Output::compute)
                .map(value -> value + ";")
                .foldRight((current, next) -> current + next)
                .map(value -> "{" + value + "}")
                .orElse("{}");
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
        return input.apply(Attribute.Type.Name)
                .asInput()
                .toOutput();
    }

    @Override
    protected boolean validate() {
        return input.is(Node.Category.Structure);
    }
}
