package com.meti.clang;

import com.meti.*;
import com.meti.feature.Node;

import java.util.List;

public class CReturnRenderer extends AbstractRenderer {
    public CReturnRenderer(Node node) {
        super(node, Node.Type.Return);
    }

    @Override
    protected Output renderDefined() {
        var prefix = new StringOutput("return ");
        var value = node.apply(Attribute.Type.Value);
        var content = new NodeOutput(value.asNode());
        var suffix = new StringOutput(";");
        return new CompoundOutput(List.of(prefix, content, suffix));
    }
}
