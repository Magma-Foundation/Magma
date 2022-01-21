package com.meti.app.compile.stage;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.function.ImplementationResolver;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.primitive.BooleanResolver;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.scope.DefinitionResolver;

public class CMagmaNodeResolver extends AfterStreamStage {
    @Override
    protected Stream<Processor<Node>> streamTypeTransformers(Node node) {
        return Streams.apply(new BooleanResolver(node));
    }

    @Override
    protected Stream<Processor<Node>> streamNodeTransformers(Node node) {
        return Streams.apply(new ImplementationResolver(node), new DefinitionResolver(node));
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
}
