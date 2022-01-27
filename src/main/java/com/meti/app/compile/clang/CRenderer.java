package com.meti.app.compile.clang;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.common.LineProcessor;
import com.meti.app.compile.common.alternate.ElseProcessor;
import com.meti.app.compile.common.binary.BinaryProcessor;
import com.meti.app.compile.common.block.BlockProcessor;
import com.meti.app.compile.common.condition.ConditionProcessor;
import com.meti.app.compile.common.integer.IntegerProcessor;
import com.meti.app.compile.common.invoke.InvocationProcessor;
import com.meti.app.compile.common.returns.ReturnProcessor;
import com.meti.app.compile.common.string.StringProcessor;
import com.meti.app.compile.common.variable.VariableProcessor;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.OutputNode;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.render.EmptyProcessor;
import com.meti.app.compile.stage.AfterStreamStage;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.Output;

public final class CRenderer extends AfterStreamStage<Output> {
    @Override
    protected Node afterDefinitionTraversal(Node transformed) throws AttributeException {
        return transformed.apply(Attribute.Type.Type).asNode();
    }

    @Override
    protected Stream<Processor<Node>> streamTypeTransformers(Input name, Node type) {
        return Streams.apply(
                new IntegerTypeRenderer(name, type),
                new FunctionTypeRenderer(name, type),
                new PrimitiveTypeRenderer(name, type),
                new ReferenceTypeRenderer(name, type),
                new StructureTypeRenderer(name, type)
        );
    }

    @Override
    protected Node wrap(Output output) {
        return new OutputNode(output);
    }

    @Override
    protected Stream<Processor<Output>> streamNodeTransformers(Node root) {
        return Streams.apply(
                new BinaryProcessor(root),
                new BlockProcessor(root),
                new ConditionProcessor(root),
                new DeclarationProcessor(root),
                new ElseProcessor(root),
                new EmptyProcessor(root),
                new ExternRenderer(root),
                new FunctionProcessor(root),
                new ImportProcessor(root),
                new IntegerProcessor(root),
                new InvocationProcessor(root),
                new LineProcessor(root),
                new ReturnProcessor(root),
                new StringProcessor(root),
                new StructureRenderer(root),
                new UnaryProcessor(root),
                new VariableProcessor(root));
    }
}
