package com.meti.app.compile.stage;

import com.meti.api.collect.stream.Stream;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;

public abstract class AfterStreamStage<T> extends StreamStage<T> {
    @Override
    protected Node afterTraversal(Node root) throws CompileException {
        return transformUsingStreams(root, streamNodeTransformers(root));
    }

    protected abstract Stream<Processor<T>> streamNodeTransformers(Node root);
}
