package com.meti.app.compile.clang.stage;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.clang.feature.function.FunctionRenderer;
import com.meti.app.compile.clang.feature.function.FunctionTypeRenderer;
import com.meti.app.compile.clang.feature.function.UnaryProcessor;
import com.meti.app.compile.clang.feature.header.ExternRenderer;
import com.meti.app.compile.clang.feature.header.ImportRenderer;
import com.meti.app.compile.clang.feature.scope.DefinitionRenderer;
import com.meti.app.compile.clang.feature.struct.StructureRenderer;
import com.meti.app.compile.clang.feature.struct.StructureTypeRenderer;
import com.meti.app.compile.clang.primitive.IntegerTypeRenderer;
import com.meti.app.compile.clang.primitive.PrimitiveTypeRenderer;
import com.meti.app.compile.clang.reference.ReferenceTypeRenderer;
import com.meti.app.compile.common.LineProcessor;
import com.meti.app.compile.common.alternate.ElseProcessor;
import com.meti.app.compile.common.binary.BinaryProcessor;
import com.meti.app.compile.common.block.BlockProcessor;
import com.meti.app.compile.common.condition.ConditionRenderer;
import com.meti.app.compile.common.integer.IntegerProcessor;
import com.meti.app.compile.common.invoke.InvocationProcessor;
import com.meti.app.compile.common.returns.ReturnProcessor;
import com.meti.app.compile.common.string.StringProcessor;
import com.meti.app.compile.common.variable.VariableProcessor;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.OutputNode;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.render.EmptyProcessor;
import com.meti.app.compile.stage.AfterStreamStage;
import com.meti.app.compile.stage.CompileException;

public final class CRenderer extends AfterStreamStage {
    @Override
    protected Node beforeDefinitionTraversal(Node definition) throws CompileException {
        if (definition.is(Node.Category.Initialization)) {
            return definition.mapAsNode(Attribute.Category.Value, this::transformNodeAST);
        } else if (definition.is(Node.Category.Declaration)) {
            return definition;
        } else {
            var format = "Not a definition:\n-----\n%s\n-----\n";
            var message = format.formatted(definition);
            throw new CompileException(message);
        }
    }

    @Override
    protected Node transformType(Node identity) throws CompileException {
        Node current = identity;
        do {
            current = transformUsingStreamsOptionally(current, streamTypeTransformers(current)).orElseThrow(() -> {
                var format = "Failed to render definition: '%s'";
                var message = format.formatted(identity);
                return new CompileException(message);
            });
        } while (!current.is(Node.Category.Output));
        return current;
    }

    @Override
    protected Stream<Processor<Node>> streamTypeTransformers(Node identity) {
        return Streams.apply(
                new IntegerTypeRenderer(identity),
                new FunctionTypeRenderer(identity),
                new PrimitiveTypeRenderer(identity),
                new ReferenceTypeRenderer(identity),
                new StructureTypeRenderer(identity)
        );
    }

    @Override
    protected Stream<Processor<Node>> streamNodeTransformers(Node root) throws StreamException {
        return Streams.apply(
                        new BinaryProcessor(root),
                        new BlockProcessor(root),
                        new ConditionRenderer(root),
                        new DefinitionRenderer(root),
                        new ElseProcessor(root),
                        new EmptyProcessor(root),
                        new ExternRenderer(root),
                        new FunctionRenderer(root),
                        new ImportRenderer(root),
                        new IntegerProcessor(root),
                        new InvocationProcessor(root),
                        new LineProcessor(root),
                        new ReturnProcessor(root),
                        new StringProcessor(root),
                        new StructureRenderer(root),
                        new UnaryProcessor(root),
                        new VariableProcessor(root))
                .map(processor -> () -> processor.process().map(OutputNode::new));
    }
}
