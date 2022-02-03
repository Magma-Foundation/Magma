package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.feature.function.ImplementationParser;
import com.meti.app.compile.feature.function.InvocationParser;
import com.meti.app.compile.feature.scope.Declaration;
import com.meti.app.compile.feature.util.LineParser;
import com.meti.app.compile.magma.FunctionType;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.primitive.Primitive;

public final class MagmaModificationStage extends AbstractModificationStage implements VisitationStage<Modifier> {
    public MagmaModificationStage(List<? extends Node> input) {
        super(input);
    }

    @Override
    protected State createInitialState() {
        return new State().mapScope(scope -> {
            // TODO: Do generic type here later
            var type = new FunctionType(Primitive.Void);
            return scope.define(new Declaration("=", type));
        });
    }

    @Override
    public Stream<Modifier> streamVisitors(State state) {
        return Streams.apply(
                new BinaryParser(state),
                new BlockVisitor(state),
                new BooleanParser(state),
                new ImplementationParser(state),
                new InvocationParser(state),
                new LineParser(state),
                new VariableVisitor(state));
    }
}
