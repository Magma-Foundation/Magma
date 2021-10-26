package com.meti.app.compile.feature;

import com.meti.app.clang.AbstractRenderer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.output.*;

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
