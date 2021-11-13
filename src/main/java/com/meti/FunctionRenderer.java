package com.meti;

import com.meti.attribute.Attribute;
import com.meti.node.Node;

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
