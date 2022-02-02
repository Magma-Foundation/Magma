package com.meti.app.compile.clang;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.OutputNode;
import com.meti.app.compile.text.Output;

public abstract class OutputRenderer extends AbstractTypeRenderer<Output> {
    protected OutputRenderer(Node identity, Node.Type type) {
        super(identity, type);
    }

    @Override
    protected Node toNode(Output node) {
        return new OutputNode(node);
    }
}
