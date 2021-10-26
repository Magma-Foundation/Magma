package com.meti.app.compile.feature;

import com.meti.app.clang.AbstractRenderer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.output.CompoundOutput;
import com.meti.app.compile.node.output.NodeOutput;
import com.meti.app.compile.node.output.Output;
import com.meti.app.compile.node.output.StringOutput;

import java.util.List;

public class ReturnRenderer extends AbstractRenderer {
    public ReturnRenderer(Node node) {
        super(node, Node.Type.Return);
    }

    @Override
    protected Output processDefined() throws AttributeException {
        var prefix = new StringOutput("return ");
        var content = new NodeOutput(node.apply(Attribute.Type.Value).asNode());
        var suffix = new StringOutput(";");
        return new CompoundOutput(List.of(prefix, content, suffix));
    }
}
