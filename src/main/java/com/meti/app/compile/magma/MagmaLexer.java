package com.meti.app.compile.magma;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.common.ReferenceLexer;
import com.meti.app.compile.common.alternate.ElseLexer;
import com.meti.app.compile.common.binary.BinaryLexer;
import com.meti.app.compile.common.block.BlockLexer;
import com.meti.app.compile.common.bool.BooleanLexer;
import com.meti.app.compile.common.condition.ConditionLexer;
import com.meti.app.compile.common.integer.IntegerLexer;
import com.meti.app.compile.common.integer.IntegerTypeLexer;
import com.meti.app.compile.common.invoke.InvocationLexer;
import com.meti.app.compile.common.returns.ReturnLexer;
import com.meti.app.compile.common.string.StringLexer;
import com.meti.app.compile.common.variable.VariableLexer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.PrimitiveLexer;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.stage.StreamStage;
import com.meti.app.compile.text.Input;

public class MagmaLexer extends StreamStage {
    @Override
    protected Node beforeTraversal(Node root) throws CompileException {
        return transformUsingStreams(root, streamNodeTransformers(root));
    }

    @Override
    protected Stream<Processor<Node>> streamNodeTransformers(Node node) throws CompileException {
        try {
            if (node.is(Node.Type.Input)) {
                return streamNodeTransformers(node.apply(Attribute.Type.Value).asInput());
            } else {
                return Streams.empty();
            }
        } catch (AttributeException e) {
            throw new CompileException(e);
        }
    }

    private Stream<Processor<Node>> streamNodeTransformers(com.meti.app.compile.text.Input input) {
        return Streams.apply(
                new ElseLexer(input),
                new StringLexer(input),
                new ConditionLexer(input),
                new BooleanLexer(input),
                new ImportLexer(input),
                new StructureLexer(input),
                new BlockLexer(input),
                new FunctionLexer(input),
                new DefinitionLexer(input),
                new InvocationLexer(input),
                new ReturnLexer(input),
                new IntegerLexer(input),
                new BinaryLexer(input),
                new UnaryLexer(input),
                new VariableLexer(input));
    }

    @Override
    protected Stream<Processor<Node>> streamTypeTransformers(Node node) throws CompileException {
        try {
            if (node.is(Node.Type.Input)) {
                var input = node.apply(Attribute.Type.Value).asInput();
                return streamTypeTransformers(input);
            } else {
                return Streams.empty();
            }
        } catch (AttributeException e) {
            throw new CompileException(e);
        }
    }

    private Stream<Processor<Node>> streamTypeTransformers(Input input) {
        return Streams.apply(new FunctionTypeLexer(input),
                new ReferenceLexer(input),
                new PrimitiveLexer(input),
                new IntegerTypeLexer(input));
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
