package com.meti.app.compile.stage;

import com.meti.app.compile.node.Node;

public abstract class AfterNodeStreamStage extends AfterStreamStage<Node> {
    @Override
    protected Node wrap(Node node) {
        return node;
    }
}
