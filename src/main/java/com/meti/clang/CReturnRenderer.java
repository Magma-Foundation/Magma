package com.meti.clang;

import com.meti.attribute.Attribute;
import com.meti.feature.Node;
import com.meti.output.*;

import java.util.List;

public class CReturnRenderer extends AbstractRenderer {
    public CReturnRenderer(Node node) {
        super(node, Node.Type.Return);
    }

    @Override
    protected Output renderDefined() {
        var prefix = new StringOutput("return ");
        var value = node.apply(Attribute.Type.Value);
        return value.<Output, RuntimeException>map(v -> {
            var content = new NodeOutput(v.asNode());
            var suffix = new StringOutput(";");
            return new CompoundOutput(List.of(prefix, content, suffix));
        }).orElse(new EmptyOutput());
    }
}
