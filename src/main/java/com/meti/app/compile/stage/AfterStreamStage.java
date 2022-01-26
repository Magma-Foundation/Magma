package com.meti.app.compile.stage;

import com.meti.api.collect.stream.Stream;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;

public abstract class AfterStreamStage extends StreamStage {
    @Override
    protected Node afterTraversal(Node root) throws CompileException {
        return transformUsingStreams(root, streamNodeTransformers(root));
    }

    protected abstract Stream<Processor<Node>> streamNodeTransformers(Node root);
}
