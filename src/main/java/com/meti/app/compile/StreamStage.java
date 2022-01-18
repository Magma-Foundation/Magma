package com.meti.app.compile;

import com.meti.api.collect.stream.EmptyStream;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.node.Node;

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

    protected Stream<Transformer> streamTypeTransformers(Node node) {
        return new EmptyStream<>();
    }

    @Override
    public Node apply(Node node) throws CompileException {
        return transformUsingStreams(node, streamNodeTransformers(node));
    }

    private Node transformUsingStreams(Node node, Stream<Transformer> transformers) throws CompileException {
        try {
            return transformers.map(Transformer::transform)
                    .flatMap(Streams::optionally)
                    .first()
                    .orElse(node);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected Stream<Transformer> streamNodeTransformers(Node node) {
        return new EmptyStream<>();
    }
}
