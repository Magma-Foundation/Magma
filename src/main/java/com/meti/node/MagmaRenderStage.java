package com.meti.node;

public final class MagmaRenderStage extends RenderStage {

    public MagmaRenderStage(Node root) {
        super(root);
    }

    @Override
    protected Renderer createRenderer(Node node) {
        return new MagmaRenderer(node);
    }

    @Override
    protected RenderStage copy(Node node) {
        return new MagmaRenderStage(node);
    }
}