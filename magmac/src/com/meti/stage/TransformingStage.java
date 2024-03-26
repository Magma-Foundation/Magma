package com.meti.stage;

import com.meti.node.Node;

import java.util.Optional;
import java.util.Stack;

public abstract class TransformingStage extends Stage<Node, Node> {
    protected Node createOutput(Node o) {
        return o;
    }

    protected Node createInput(Node node) {
        return node;
    }

    @Override
    public Optional<Node> applyToNode(Node node, Stack<Node> b) {
        return applyWithStack(createInput(node), b).map(this::createOutput);
    }
}
