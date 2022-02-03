package com.meti.app.compile.feature.util;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.parse.AbstractParser;
import com.meti.app.compile.parse.State;

public class LineParser extends AbstractParser {
    public LineParser(State state) {
        super(state);
    }

    @Override
    protected boolean isValid() {
        return state.applyToCurrent(current -> current.is(Node.Category.Line));
    }

    @Override
    protected State onEnterImpl() {
        return state.enter();
    }

    @Override
    protected State onExitImpl() {
        return state.exit();
    }
}
