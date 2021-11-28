package com.meti.app.process.clang;

import com.meti.app.CompileException;
import com.meti.app.attribute.Attribute;
import com.meti.app.node.Node;
import com.meti.app.process.FilteredRenderer;

public class DeclarationRenderer extends FilteredRenderer {
    public DeclarationRenderer(Node value) {
        super(value, Node.Type.Declaration);
    }

    @Override
    protected String processValid() throws CompileException {
        return value.apply(Attribute.Type.Value)
                       .asNode()
                       .apply(Attribute.Type.Value)
                       .asString() + ";";
    }
}
