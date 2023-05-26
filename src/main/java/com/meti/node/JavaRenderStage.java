package com.meti.node;

public class JavaRenderStage extends RenderStage {
    public JavaRenderStage(Node root) {
        super(root);
    }

    @Override
    protected Renderer createRenderer(Node node) {
        return new JavaRenderer(node);
    }

    @Override
    protected RenderStage copy(Node node) {
        return new JavaRenderStage(node);
    }
}
