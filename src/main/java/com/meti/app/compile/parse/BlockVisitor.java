package com.meti.app.compile.parse;

import com.meti.app.compile.node.Node;

public class BlockVisitor extends AbstractParser {
    public BlockVisitor(State state) {
        super(state);
    }

    @Override
    protected boolean isValid(State state) {
        return state.queryCurrent(current -> current.is(Node.Type.Block));
    }

    @Override
    protected State onEnterImpl() {
        return state.mapScope(Scope::enter);
    }

    @Override
    protected State onExitImpl() {
        return state.mapScope(Scope::exit);
    }
}
