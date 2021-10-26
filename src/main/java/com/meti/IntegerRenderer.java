package com.meti;

import com.meti.clang.AbstractRenderer;
import com.meti.feature.Node;

public class IntegerRenderer extends AbstractRenderer {
    public IntegerRenderer(Node node) {
        super(node, Node.Type.Integer);
    }

    @Override
    protected Output renderDefined() {
        var value = node.apply(Attribute.Type.Value).asInteger();
        return new StringOutput(String.valueOf(value));
    }
}
