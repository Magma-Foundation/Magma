package com.meti.app.process.clang;

import com.meti.app.attribute.Attribute;
import com.meti.app.attribute.AttributeException;
import com.meti.app.node.Node;
import com.meti.app.process.FilteredRenderer;

class ReturnRenderer extends FilteredRenderer {
    public ReturnRenderer(Node node) {
        super(node, Node.Type.Return);
    }

    @Override
    protected String processValid() throws AttributeException {
        return "return " + renderChild() + ";";
    }

    private String renderChild() throws AttributeException {
        return value.apply(Attribute.Type.Value)
                .asNode()
                .apply(Attribute.Type.Value)
                .asString();
    }
}
