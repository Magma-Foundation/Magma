package com.meti.app.process;

import com.meti.app.attribute.Attribute;
import com.meti.app.attribute.AttributeException;
import com.meti.app.node.Node;

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
