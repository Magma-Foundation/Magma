package com.meti;

import com.meti.option.Option;

class IntegerRenderer extends AbstractRenderer {
    public IntegerRenderer(Node node) {
        super(node);
    }

    @Override
    public Option<String> process() {
        return node.getValueAsInteger()
                .filter(value -> node.is(Node.Type.Integer))
                .map(String::valueOf);
    }
}
