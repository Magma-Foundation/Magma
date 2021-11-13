package com.meti;

public class IntegerRenderer extends AbstractRenderer {
    public IntegerRenderer(Node node) {
        super(node, Node.Type.Int);
    }

    @Override
    protected String renderValid() throws AttributeException {
        var valueAsInt = value.apply().asInt();
        return String.valueOf(valueAsInt);
    }
}
