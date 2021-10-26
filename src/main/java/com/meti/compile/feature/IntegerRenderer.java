package com.meti.compile.feature;

import com.meti.clang.AbstractRenderer;
import com.meti.compile.node.Node;
import com.meti.compile.node.attribute.Attribute;
import com.meti.compile.node.output.Output;
import com.meti.compile.node.output.StringOutput;

public class IntegerRenderer extends AbstractRenderer {
    public IntegerRenderer(Node node) {
        super(node, Node.Type.Integer);
    }

    @Override
    protected Output processDefined() {
        var value = node.apply(Attribute.Type.Value)
                .map(Attribute::asInteger)
                .orElse(-1);

        return new StringOutput(String.valueOf(value));
    }
}
