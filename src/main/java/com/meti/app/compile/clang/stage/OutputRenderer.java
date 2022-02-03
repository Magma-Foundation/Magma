package com.meti.app.compile.clang.stage;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.OutputNode;
import com.meti.app.compile.text.Output;

public abstract class OutputRenderer extends AbstractTypeRenderer<Output> {
    protected OutputRenderer(Node identity, Node.Category category) {
        super(identity, category);
    }

    @Override
    protected Node toNode(Output node) {
        return new OutputNode(node);
    }
}
