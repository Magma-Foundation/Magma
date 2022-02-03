package com.meti.app.compile.clang.reference;

import com.meti.app.compile.clang.stage.NodeTypeRenderer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.OutputAttribute;
import com.meti.app.compile.node.attribute.TypeAttribute;
import com.meti.app.compile.stage.CompileException;

public class ReferenceTypeRenderer extends NodeTypeRenderer {
    public ReferenceTypeRenderer(Node identity) {
        super(identity, Node.Category.Reference);
    }

    @Override
    protected Node processImpl() throws CompileException {
        var name = identity.apply(Attribute.Category.Name)
                .asInput()
                .toOutput()
                .prepend("*");
        var innerType = identity
                .apply(Attribute.Category.Type).asType()
                .apply(Attribute.Category.Value).asType();

        return identity.with(Attribute.Category.Name, new OutputAttribute(name))
                .with(Attribute.Category.Type, new TypeAttribute(innerType));
    }
}
