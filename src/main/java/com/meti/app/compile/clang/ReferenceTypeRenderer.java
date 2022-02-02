package com.meti.app.compile.clang;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.OutputAttribute;
import com.meti.app.compile.stage.CompileException;

public class ReferenceTypeRenderer extends AbstractTypeRenderer {
    public ReferenceTypeRenderer(Node identity) {
        super(identity, Node.Type.Reference);
    }

    @Override
    protected Node processValid() throws CompileException {
        var name = identity.apply(Attribute.Type.Name)
                .asInput()
                .toOutput()
                .prepend("*");
        return identity.with(Attribute.Type.Name, new OutputAttribute(name));
    }
}
