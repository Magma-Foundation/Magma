package com.meti.app.clang;

import com.meti.app.compile.CompileException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.output.Output;
import com.meti.app.compile.node.output.StringOutput;

public class ContentRenderer extends AbstractRenderer {
    public ContentRenderer(Node node) {
        super(node, Node.Type.Content);
    }

    @Override
    protected Output processDefined() throws CompileException {
        return new StringOutput(node.apply(Attribute.Type.Value).asInput().compute());
    }
}
