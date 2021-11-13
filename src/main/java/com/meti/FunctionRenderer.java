package com.meti;

public class FunctionRenderer extends AbstractRenderer {
    public FunctionRenderer(Node value) {
        super(value, Node.Type.Function);
    }

    @Override
    protected String renderValid() throws CompileException {
        var identity = value.apply(Attribute.Type.Identity).asNode().apply(Attribute.Type.Value).asString();
        var valueString = value.apply(Attribute.Type.Value).asNode().apply(Attribute.Type.Value).asString();
        return identity + valueString;
    }
}
