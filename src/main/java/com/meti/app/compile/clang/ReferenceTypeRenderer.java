package com.meti.app.compile.clang;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.Output;

public class ReferenceTypeRenderer extends AbstractTypeRenderer {
    public ReferenceTypeRenderer(Input name, Node type) {
        super(name, type, Node.Type.Reference);
    }

    @Override
    protected Output processValid() throws CompileException {
        return type.apply(Attribute.Type.Value)
                .asOutput()
                .appendSlice(" *")
                .appendOutput(name.toTrimmedOutput());
    }
}
