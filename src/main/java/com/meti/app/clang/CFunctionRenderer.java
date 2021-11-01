package com.meti.app.clang;

import com.meti.app.compile.CompileException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.output.Output;
import com.meti.app.compile.node.output.StringOutput;

public class CFunctionRenderer extends AbstractRenderer {
    public CFunctionRenderer(Node node) {
        super(node, Node.Type.Function);
    }

    @Override
    protected Output processDefined() throws CompileException {
        var name = node.apply(Attribute.Type.Name).asInput().compute();
        return new StringOutput("void " + name + "(){}");
    }
}
