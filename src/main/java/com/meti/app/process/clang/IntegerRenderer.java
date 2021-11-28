package com.meti.app.process.clang;

import com.meti.app.attribute.Attribute;
import com.meti.app.attribute.AttributeException;
import com.meti.app.node.Node;
import com.meti.app.process.FilteredRenderer;

public class IntegerRenderer extends FilteredRenderer {
    public IntegerRenderer(Node node) {
        super(node, Node.Type.Int);
    }

    @Override
    protected String processValid() throws AttributeException {
        var valueAsInt = value.apply(Attribute.Type.Value).asInt();
        return String.valueOf(valueAsInt);
    }
}
