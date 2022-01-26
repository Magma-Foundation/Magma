package com.meti.app.compile.magma;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.stage.StreamStage;

public class NodeStage extends StreamStage<Node> {
    @Override
    protected Node wrap(Node node) {
        return node;
    }
}
