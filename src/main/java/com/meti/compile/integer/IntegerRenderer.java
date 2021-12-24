package com.meti.compile.integer;

import com.meti.compile.AbstractRenderer;
import com.meti.compile.Node;
import com.meti.compile.RenderException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
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
