package com.meti.app.compile.clang;

import com.meti.app.compile.node.Node;

public abstract class NodeTypeRenderer extends AbstractTypeRenderer<Node> {
    public NodeTypeRenderer(Node identity, Node.Type type) {
        super(identity, type);
    }

    @Override
    protected Node toNode(Node node) {
        return node;
    }
}
