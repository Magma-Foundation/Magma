package com.meti.stage;

import com.meti.magma.MagmaRenderer;
import com.meti.node.Node;

public class RenderingStage extends MagmaRenderingStage {
    @Override
    protected Renderer createRenderer(Node node) {
        return new MagmaRenderer(node);
    }
}
