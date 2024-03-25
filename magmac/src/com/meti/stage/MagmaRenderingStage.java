package com.meti.stage;

import com.meti.node.Content;
import com.meti.node.Node;

import java.util.Optional;

public abstract class MagmaRenderingStage extends Stage<Node, String> {
    @Override
    public Optional<Node> onEnter(Node value) {
        return Optional.of(value);
    }

    @Override
    protected Node createOutput(String o) {
        return new Content("?", o, 0);
    }

    @Override
    protected Node createInput(Node node) {
        return node;
    }

    @Override
    protected Optional<String> onExit(Node node) {
        return createRenderer(node).render();
    }

    protected abstract Renderer createRenderer(Node node);
}
