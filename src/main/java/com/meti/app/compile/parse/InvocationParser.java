package com.meti.app.compile.parse;

import com.meti.app.compile.node.Node;

public class InvocationParser extends AbstractParser {
    protected InvocationParser(State state) {
        super(state);
    }

    @Override
    protected boolean isValid(State state) {
        return state.queryCurrent(value -> value.is(Node.Type.Invocation));
    }
}
