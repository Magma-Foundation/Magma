package com.meti;

import com.meti.option.Option;

class IntegerRenderer extends AbstractRenderer {
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
