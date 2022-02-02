package com.meti.app.compile.clang.reference;

import com.meti.app.compile.clang.NodeTypeRenderer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.OutputAttribute;
import com.meti.app.compile.node.attribute.TypeAttribute;
import com.meti.app.compile.stage.CompileException;

public class ReferenceTypeRenderer extends NodeTypeRenderer {
    public ReferenceTypeRenderer(Node identity) {
        super(identity, Node.Role.Reference);
    }

    @Override
    protected Node processImpl() throws CompileException {
        var name = identity.apply(Attribute.Type.Name)
                .asInput()
                .toOutput()
                .prepend("*");
        var innerType = identity
                .apply(Attribute.Type.Type).asType()
                .apply(Attribute.Type.Value).asType();

        return identity.with(Attribute.Type.Name, new OutputAttribute(name))
                .with(Attribute.Type.Type, new TypeAttribute(innerType));
    }
}
