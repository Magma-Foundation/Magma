package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.feature.function.ImplementationParser;
import com.meti.app.compile.feature.function.InvocationParser;
import com.meti.app.compile.feature.util.LineParser;
import com.meti.app.compile.node.Node;

public class CMagmaModificationStage extends AbstractVisitationStage<Modifier> implements VisitationStage<Modifier> {
    public CMagmaModificationStage(List<? extends Node> input) {
        super(input);
    }

    @Override
    protected State createInitialState() {
        return new State();
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
