package com.meti.app.compile.magma.lex;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.common.ReferenceTypeLexer;
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
import com.meti.app.compile.lex.LexException;
import com.meti.app.compile.magma.*;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.TypeAttribute;
import com.meti.app.compile.primitive.PrimitiveLexer;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Input;

public class MagmaLexer extends NodeStage {
    @Override
    protected Node beforeDefinitionTraversal(Node definition) throws CompileException {
        if (definition.is(Node.Category.Input)) {
            var input = definition.apply(Attribute.Type.Value).asInput();
            return new FieldLexer(input).process().orElseThrow(() -> {
                var format = "Invalid definition:\n-----\n%s\n-----\n";
                var message = format.formatted(definition);
                return new LexException(message);
            });
        } else {
            return definition;
        }
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
    protected Stream<Processor<Node>> streamTypeTransformers(Node identity) throws CompileException {
        if (identity.is(Node.Category.Declaration) || identity.is(Node.Category.Initialization)) {
            var type = identity.apply(Attribute.Type.Type).asType();
            if (type.is(Node.Category.Input)) {
                var input = type.apply(Attribute.Type.Value).asInput();
                try {
                    return Streams.apply(new FunctionTypeLexer(input),
                            new ReferenceTypeLexer(input),
                            new PrimitiveLexer(input),
                            new IntegerTypeLexer(input))
                            .map(lexer -> () -> lexer.process().map(result -> identity.with(Attribute.Type.Type, new TypeAttribute(result))));
                } catch (StreamException e) {
                    throw new CompileException(e);
                }
            } else {
                return Streams.empty();
            }
        } else {
            return Streams.empty();
        }
    }

    @Override
    protected Node beforeNodeTraversal(Node root) throws CompileException {
        try {
            if (root.is(Node.Category.Input)) {
                var input = root.apply(Attribute.Type.Value).asInput();
                return transformUsingStreams(root, streamNodeLexers(input));
            } else {
                return root;
            }
        } catch (AttributeException e) {
            throw new CompileException(e);
        }
    }

    private Stream<Processor<Node>> streamNodeLexers(Input input) {
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
}
