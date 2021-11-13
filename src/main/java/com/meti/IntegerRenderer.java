package com.meti;

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
