package com.meti.app.compile.clang.primitive;

import com.meti.app.compile.clang.OutputRenderer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.text.Output;

public class PrimitiveTypeRenderer extends OutputRenderer {
    public PrimitiveTypeRenderer(Node identity) {
        super(identity, Node.Category.Primitive);
    }

    @Override
    protected Output processImpl() throws AttributeException {
        var type = identity.apply(Attribute.Type.Type)
                .asType()
                .apply(Attribute.Type.Name)
                .asOutput()
                .map(String::toLowerCase);

        var name = identity.apply(Attribute.Type.Name).asInput().toOutput();
        return type.appendSlice(" ").appendOutput(name);
    }
}
