package com.meti.app.compile.clang;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.text.StringOutput;

public class ReferenceTypeRenderer extends AbstractTypeRenderer {
    public ReferenceTypeRenderer(Input name, Node type) {
        super(name, type, Node.Type.Reference);
    }

    @Override
    protected Output processValid() throws CompileException {
        return new StringOutput();
    }
}
