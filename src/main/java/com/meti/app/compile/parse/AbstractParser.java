package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.stage.CompileException;

public abstract class AbstractParser extends AbstractVisitationStage {
    public AbstractParser(List<? extends Node> input) {
        super(input);
    }


    @Override
    protected State parseImpl(State state) throws CompileException {
        return transformAST(state, Parser::onParse);
    }
}
