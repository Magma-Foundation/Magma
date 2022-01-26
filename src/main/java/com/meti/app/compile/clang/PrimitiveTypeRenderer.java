package com.meti.app.compile.clang;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.Output;

public class PrimitiveTypeRenderer extends AbstractTypeRenderer {
    public PrimitiveTypeRenderer(Input name, Node type) {
        super(name, type, Node.Type.Primitive);
    }

    @Override
    protected Output processValid() throws CompileException {
        return type.apply(Attribute.Type.Name)
                .asInput()
                .toOutput()
                .map(String::toLowerCase)
                .appendSlice(" ")
                .appendOutput(name.toOutput());
    }
}
