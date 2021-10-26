package com.meti.compile.feature;

import com.meti.clang.AbstractRenderer;
import com.meti.compile.Node;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.output.Output;
import com.meti.compile.output.StringOutput;

public class IntegerRenderer extends AbstractRenderer {
    public IntegerRenderer(Node node) {
        super(node, Node.Type.Integer);
    }

    @Override
    protected Output processValid() {
        var value = node.apply(Attribute.Type.Value)
                .map(Attribute::asInteger)
                .orElse(-1);

        return new StringOutput(String.valueOf(value));
    }
}
