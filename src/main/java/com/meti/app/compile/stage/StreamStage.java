package com.meti.app.compile.stage;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.process.Processor;

public abstract class StreamStage extends AbstractStage {
    @Override
    protected Node transformDefinition(Node definition) throws CompileException {
        var newDefinition = beforeDefinitionTraversal(definition);

        if (newDefinition.is(Node.Type.Initialization)) {
            var withType = transformTypeAttribute(newDefinition);
            return transformNodeAttribute(withType, Attribute.Type.Value);
        } else if (newDefinition.is(Node.Type.Declaration)) {
            return transformTypeAttribute(newDefinition);
        } else {
            return newDefinition;
        }
    }

    protected Node beforeDefinitionTraversal(Node definition) throws CompileException {
        return definition;
    }

    @Override
    protected Node transformType(Node type) throws CompileException {
        return transformUsingStreams(type, streamTypeTransformers(type));
    }

    protected abstract Node transformUsingStreams(Node node, Stream<Processor<Node>> transformers) throws CompileException;

    protected Stream<Processor<Node>> streamTypeTransformers(Node node) throws CompileException {
        return Streams.empty();
    }
}
