package com.meti.integer;

import com.meti.AbstractRenderer;
import com.meti.Node;
import com.meti.RenderException;
import com.meti.attribute.Attribute;
import com.meti.attribute.AttributeException;
import com.meti.option.Option;

public class IntegerRenderer extends AbstractRenderer {
    public IntegerRenderer(Node node) {
        super(node);
    }

    @Override
    public Option<String> process() throws RenderException {
        try {
            return node.getValue().map(Attribute::asInteger)
                    .filter(value -> node.is(Node.Type.Integer))
                    .map(String::valueOf);
        } catch (AttributeException e) {
            throw new RenderException(e);
        }
    }
}
