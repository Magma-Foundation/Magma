package com.meti.app.compile.feature;

import com.meti.app.clang.AbstractRenderer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.output.Output;
import com.meti.app.compile.node.output.StringOutput;

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
