package com.meti.app.compile.parse;

import com.meti.app.compile.node.Node;

public class BlockParser extends UnitedParser {
    @Override
    protected boolean isValid(State state) {
        return state.queryCurrent(current -> current.is(Node.Type.Block));
    }

    @Override
    protected State onEnterImpl(State state) {
        return state.mapScope(Scope::enter);
    }

    @Override
    protected State onExitImpl(State state) {
        return state.mapScope(Scope::exit);
    }
}
