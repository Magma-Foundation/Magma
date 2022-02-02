package com.meti.app.compile.stage;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.process.Processor;

public abstract class StreamStage extends AbstractStage {
    @Override
    protected Node transformDefinition(Node definition) throws CompileException {
        var newDefinition = beforeDefinitionTraversal(definition);
        var transformed = transformDefinitionImpl(newDefinition);
        return afterDefinitionTraversal(transformed);
    }

    protected Node beforeDefinitionTraversal(Node definition) throws CompileException {
        return definition;
    }

    private Node transformDefinitionImpl(Node newDefinition) throws CompileException {
        if (newDefinition.is(Node.Type.Initialization)) {
            var withType = transformTypeAttribute(newDefinition);
            return transformNodeAttribute(withType, Attribute.Type.Value);
        } else if (newDefinition.is(Node.Type.Declaration)) {
            return transformTypeAttribute(newDefinition);
        } else {
            return newDefinition;
        }
    }

    protected Node afterDefinitionTraversal(Node transformed) throws CompileException {
        return transformed;
    }

    @Override
    protected Node transformType(Node identity) throws CompileException {
        return transformUsingStreams(identity, streamTypeTransformers(identity));
    }

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

    protected Stream<Processor<Node>> streamTypeTransformers(Node identity) throws CompileException {
        return Streams.empty();
    }
}
