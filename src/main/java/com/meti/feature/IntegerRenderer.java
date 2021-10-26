package com.meti.feature;

import com.meti.attribute.Attribute;
import com.meti.clang.AbstractRenderer;
import com.meti.output.Output;
import com.meti.output.StringOutput;

public class IntegerRenderer extends AbstractRenderer {
    public IntegerRenderer(Node node) {
        super(node, Node.Type.Integer);
    }

    @Override
    protected Output renderDefined() {
        var value = node.apply(Attribute.Type.Value)
                .map(Attribute::asInteger)
                .orElse(-1);

        return new StringOutput(String.valueOf(value));
    }
}
