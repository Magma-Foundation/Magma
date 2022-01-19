package com.meti.app.compile.stage;

import com.meti.api.collect.stream.EmptyStream;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.process.Processor;

public abstract class StreamStage extends AbstractStage {
    @Override
    protected Node transformDefinition(Node definition) throws CompileException {
        if (definition.is(Node.Type.Initialization)) {
            var withType = transformTypeAttribute(definition);
            return transformNodeAttribute(withType, Attribute.Type.Value);
        } else if (definition.is(Node.Type.Declaration)) {
            return transformTypeAttribute(definition);
        } else {
            return definition;
        }
    }

    @Override
    protected Node transformType(Node type) throws CompileException {
        return transformUsingStreams(type, streamTypeTransformers(type));
    }

    protected Stream<Processor<Node>> streamNodeTransformers(Node node) {
        return new EmptyStream<>();
    }

    @Override
    public Node apply(Node node) throws CompileException {
        return transformUsingStreams(node, streamNodeTransformers(node));
    }

    protected Stream<Processor<Node>> streamTypeTransformers(Node node) {
        return new EmptyStream<>();
    }

    private Node transformUsingStreams(Node node, Stream<Processor<Node>> transformers) throws CompileException {
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
