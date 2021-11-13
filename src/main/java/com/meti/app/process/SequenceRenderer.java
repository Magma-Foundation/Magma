package com.meti.app.process;

import com.meti.api.stream.StreamException;
import com.meti.app.attribute.Attribute;
import com.meti.app.attribute.AttributeException;
import com.meti.app.node.Node;

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
