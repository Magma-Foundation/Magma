package com.meti.app.process.clang;

import com.meti.app.CompileException;
import com.meti.app.attribute.Attribute;
import com.meti.app.node.Node;
import com.meti.app.process.FilteredRenderer;

public class FunctionRenderer extends FilteredRenderer {
    public FunctionRenderer(Node value) {
        super(value, Node.Type.Function);
    }

    @Override
    protected String processValid() throws CompileException {
        var identity = value.apply(Attribute.Type.Identity).asNode().apply(Attribute.Type.Value).asString();
        var valueString = value.apply(Attribute.Type.Value).asNode().apply(Attribute.Type.Value).asString();
        return identity + valueString;
    }
}
