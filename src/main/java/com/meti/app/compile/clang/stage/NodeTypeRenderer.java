package com.meti.app.compile.clang.stage;

import com.meti.app.compile.node.Node;

public abstract class NodeTypeRenderer extends AbstractTypeRenderer<Node> {
    public NodeTypeRenderer(Node identity, Node.Category category) {
        super(identity, category);
    }

    @Override
    protected Node toNode(Node node) {
        return node;
    }
}
