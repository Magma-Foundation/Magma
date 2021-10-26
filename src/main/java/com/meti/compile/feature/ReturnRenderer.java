package com.meti.compile.feature;

import com.meti.clang.AbstractRenderer;
import com.meti.compile.node.Node;
import com.meti.compile.node.attribute.Attribute;
import com.meti.compile.node.output.*;

import java.util.List;

public class ReturnRenderer extends AbstractRenderer {
    public ReturnRenderer(Node node) {
        super(node, Node.Type.Return);
    }

    @Override
    protected Output processDefined() {
        var prefix = new StringOutput("return ");
        var value = node.apply(Attribute.Type.Value);
        return value.<Output, RuntimeException>map(v -> {
            var content = new NodeOutput(v.asNode());
            var suffix = new StringOutput(";");
            return new CompoundOutput(List.of(prefix, content, suffix));
        }).orElse(new EmptyOutput());
    }
}
