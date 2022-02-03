package com.meti.app.compile.parse;

import com.meti.app.compile.node.Node;

public class BlockParser extends AbstractParser {
    public BlockParser(State state) {
        super(state);
    }

    @Override
    protected boolean isValid() {
        return state.applyToCurrent(current -> current.is(Node.Category.Block));
    }

    @Override
    protected State onEnterImpl() {
        return state.mapScope(scope -> scope.enter(state.getCurrent()));
    }

    @Override
    protected State onExitImpl() {
        return state.mapScope(Scope::exit);
    }
}
