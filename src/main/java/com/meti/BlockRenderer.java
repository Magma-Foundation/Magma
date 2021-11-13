package com.meti;

class BlockRenderer extends AbstractRenderer {
    public BlockRenderer(Node value) {
        super(value, Node.Type.Block);
    }

    @Override
    protected String renderValid() throws AttributeException {
        return "{" + renderChildren() + "}";
    }

    private String renderChildren() throws AttributeException {
        try {
            return value.apply(Attribute.Type.Children)
                    .asNodeStream()
                    .map(child -> child.apply(Attribute.Type.Value))
                    .map(Attribute::asString)
                    .foldRight((sum, next) -> sum + next)
                    .orElse("");
        } catch (StreamException e) {
            throw new AttributeException("Failed to render children.", e);
        }
    }
}
