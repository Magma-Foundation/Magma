package com.meti.app.compile.clang.reference;

import com.meti.app.compile.clang.NodeTypeRenderer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.OutputAttribute;
import com.meti.app.compile.stage.CompileException;

public class ReferenceTypeRenderer extends NodeTypeRenderer {
    public ReferenceTypeRenderer(Node identity) {
        super(identity, Node.Type.Reference);
    }

    @Override
    protected Node processImpl() throws CompileException {
        var name = identity.apply(Attribute.Type.Name)
                .asInput()
                .toOutput()
                .prepend("*");
        var innerType = identity.apply(Attribute.Type.Type).asNode()
                .apply(Attribute.Type.Value).asNode();
        return identity.with(Attribute.Type.Name, new OutputAttribute(name))
                .with(Attribute.Type.Type, new NodeAttribute(innerType));
    }
}
