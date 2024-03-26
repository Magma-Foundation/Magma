package com.meti.stage;

import com.meti.node.Node;

import java.util.Optional;

public abstract class TransformingStage extends Stage<Node, Node> {
    protected Node createOutput(Node o) {
        return o;
    }

    protected Node createInput(Node node) {
        return node;
    }

    @Override
    public Optional<Node> applyToNode(Node node) {
        return apply(createInput(node)).map(this::createOutput);
    }
}
