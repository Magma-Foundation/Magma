package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.app.compile.node.Node;

public class CMagmaModifier extends AbstractVisitor implements MagmaVisitor {
    public CMagmaModifier(List<? extends Node> input) {
        super(input);
    }

    @Override
    protected State createInitialState() {
        return new State();
    }
}
