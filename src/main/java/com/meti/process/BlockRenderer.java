package com.meti.process;

import com.meti.attribute.Attribute;
import com.meti.attribute.AttributeException;
import com.meti.node.Node;
import com.meti.stream.StreamException;

class BlockRenderer extends FilteredRenderer {
    public BlockRenderer(Node value) {
        super(value, Node.Type.Block);
    }

    @Override
    protected String processValid() throws AttributeException {
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
