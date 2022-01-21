package com.meti.app.compile.stage;

import com.meti.app.compile.node.Node;

public abstract class AfterStreamStage extends StreamStage {
    @Override
    protected Node afterTraversal(Node root) throws CompileException {
        return transformUsingStreams(root, streamNodeTransformers(root));
    }
}
