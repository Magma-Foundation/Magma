package com.meti.app.compile.magma;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.function.ReturnResolver;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.primitive.IntResolver;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.scope.BlockResolver;
import com.meti.app.compile.stage.CompileException;

public class MagmaTypeResolver extends NodeStage {
    @Override
    protected Node beforeNodeTraversal(Node root) throws CompileException {
        return transformUsingStreams(root, streamNodeTransformers(root));
    }

    @Override
    protected Node transformUsingStreams(Node node, Stream<Processor<Node>> transformers) throws CompileException {
        try {
            return transformers.map(Processor::process)
                    .flatMap(Streams::optionally)
                    .first()
                    .orElse(node);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private Stream<Processor<Node>> streamNodeTransformers(Node node) {
        return Streams.apply(
                new BlockResolver(node, this),
                new ReturnResolver(node, this),
                new IntResolver(node));
    }
}
