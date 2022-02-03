package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
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
        return Streams.apply();
    }

}
