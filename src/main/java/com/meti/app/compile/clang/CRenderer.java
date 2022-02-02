package com.meti.app.compile.clang;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
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
        if (definition.is(Node.Role.Initialization)) {
            return definition.mapAsNode(Attribute.Type.Value, this::transformNodeAST);
        } else if (definition.is(Node.Role.Declaration)) {
            return definition;
        } else {
            var format = "Not a definition:\n-----\n%s\n-----\n";
            var message = format.formatted(definition);
            throw new CompileException(message);
        }
    }

    @Override
    protected Node afterDefinitionTraversal(Node transformed) throws CompileException {
        if (transformed.is(Node.Role.Output)) {
            return transformed;
        } else {
            var type = transformed.apply(Attribute.Type.Type).asOutput();
            var value = transformed.apply(Attribute.Type.Value).asOutput();
            return new OutputNode(type.appendSlice("=").appendOutput(value));
        }
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
