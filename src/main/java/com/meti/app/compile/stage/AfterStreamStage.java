package com.meti.app.compile.stage;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;

public abstract class AfterStreamStage extends StreamStage {
    @Override
    protected Node afterTraversal(Node root) throws CompileException {
        try {
            return transformUsingStreams(root, streamNodeTransformers(root));
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected abstract Stream<Processor<Node>> streamNodeTransformers(Node root) throws StreamException;
}
