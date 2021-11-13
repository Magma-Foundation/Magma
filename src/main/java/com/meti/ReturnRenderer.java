package com.meti;

class ReturnRenderer extends AbstractRenderer {
    public ReturnRenderer(Node node) {
        super(node, Node.Type.Return);
    }

    @Override
    protected String renderValid() throws AttributeException {
        return "return " + value.apply().asNode().apply().asString() + ";";
    }
}
