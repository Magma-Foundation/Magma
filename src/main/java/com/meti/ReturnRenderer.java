package com.meti;

import com.meti.attribute.Attribute;
import com.meti.attribute.AttributeException;
import com.meti.node.Node;

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
