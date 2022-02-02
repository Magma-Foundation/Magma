package com.meti.app.compile.stage;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.primitive.BooleanResolver;
import com.meti.app.compile.process.Processor;

public class CMagmaNodeResolver extends AfterNodeStreamStage {
    @Override
    protected Stream<Processor<Node>> streamNodeTransformers(Node root) {
        return Streams.empty();
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

    @Override
    protected Stream<Processor<Node>> streamTypeTransformers(Node identity) {
        return Streams.apply(new BooleanResolver(node));
    }
}
