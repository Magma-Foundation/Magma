package com.meti;

import com.meti.node.Node;
import com.meti.stream.StreamException;

class SequenceRenderer extends FilteredRenderer {
    public SequenceRenderer(Node value) {
        super(value, Node.Type.Sequence);
    }

    @Override
    protected String processValid() throws AttributeException {
        return "(" + renderChildren() + ")";
    }

    private String renderChildren() throws AttributeException {
        try {
            return value.apply(Attribute.Type.Children)
                    .asNodeStream()
                    .map(child -> child.apply(Attribute.Type.Value))
                    .map(Attribute::asString)
                    .foldRight((sum, next) -> sum + "," + next)
                    .orElse("");
        } catch (StreamException e) {
            throw new AttributeException("Failed to render children.", e);
        }
    }
}
