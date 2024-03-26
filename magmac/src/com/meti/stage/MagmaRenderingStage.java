package com.meti.stage;

import com.meti.node.Content;
import com.meti.node.Node;

import java.util.Optional;
import java.util.Stack;

public abstract class MagmaRenderingStage extends Stage<Node, String> {
    @Override
    public Optional<Node> onEnter(Node value) {
        return Optional.of(value);
    }

    private Node createOutput(String o) {
        return new Content("?", o, 0);
    }

    private Node createInput(Node node) {
        return node;
    }

    @Override
    protected Optional<String> onExit(Node node, Stack<Node> b) {
        return createRenderer(node).render();
    }

    protected abstract Renderer createRenderer(Node node);

    @Override
    public Optional<Node> applyToNode(Node node, Stack<Node> b) {
        return apply(createInput(node)).map(this::createOutput);
    }
}
