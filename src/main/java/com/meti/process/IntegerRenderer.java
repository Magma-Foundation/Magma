package com.meti.process;

import com.meti.attribute.Attribute;
import com.meti.attribute.AttributeException;
import com.meti.node.Node;

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
