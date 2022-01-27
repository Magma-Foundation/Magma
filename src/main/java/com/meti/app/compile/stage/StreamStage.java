package com.meti.app.compile.stage;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Input;

public abstract class StreamStage<T> extends AbstractStage {
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

    protected Node afterDefinitionTraversal(Node transformed) throws AttributeException {
        return transformed;
    }

    @Override
    protected Node transformType(Input name, Node type) throws CompileException {
        return transformUsingStreams1(type, streamTypeTransformers(name, type));
    }

    protected Node transformUsingStreams1(Node node, Stream<Processor<Node>> transformers) throws CompileException {
        try {
            return transformers.map(Processor::process)
                    .flatMap(Streams::optionally)
                    .first()
                    .orElse(node);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected Stream<Processor<Node>> streamTypeTransformers(Input name, Node node) throws CompileException {
        return Streams.empty();
    }

    protected Node transformUsingStreams(Node node, Stream<Processor<T>> transformers) throws CompileException {
        try {
            return transformers.map(Processor::process)
                    .flatMap(Streams::optionally)
                    .first()
                    .map(this::wrap)
                    .orElse(node);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected abstract Node wrap(T t);
}
