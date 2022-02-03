package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.stage.CompileException;

public abstract class AbstractModificationStage extends AbstractVisitationStage<Modifier> {
    public AbstractModificationStage(List<? extends Node> input) {
        super(input);
    }

    @Override
    protected State modifyAfterAST(State state) throws CompileException {
        return transformAST(state, Modifier::modifyAfterAST);
    }

    @Override
    protected State modifyBeforeAST(State state) throws CompileException {
        return transformAST(state, Modifier::modifyBeforeAST);
    }
}
