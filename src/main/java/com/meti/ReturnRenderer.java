package com.meti;

class ReturnRenderer extends AbstractRenderer {
    public ReturnRenderer(Node node) {
        super(node, Node.Type.Return);
    }

    @Override
    protected String renderValid() throws AttributeException {
        return "return " + renderChild() + ";";
    }

    private String renderChild() throws AttributeException {
        return value.apply(Attribute.Type.Value)
                .asNode()
                .apply(Attribute.Type.Value)
                .asString();
    }
}
