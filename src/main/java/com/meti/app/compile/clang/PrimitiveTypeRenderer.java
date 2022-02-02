package com.meti.app.compile.clang;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.text.Output;

public class PrimitiveTypeRenderer extends OutputRenderer {
    public PrimitiveTypeRenderer(Node identity) {
        super(identity, Node.Type.Primitive);
    }

    @Override
    protected Output processValid() throws AttributeException {
        return identity.apply(Attribute.Type.Name)
                .asInput()
                .toOutput()
                .map(String::toLowerCase)
                .appendSlice(" ");
    }
}
