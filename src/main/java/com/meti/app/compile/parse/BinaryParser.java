package com.meti.app.compile.parse;

import com.meti.app.compile.common.Line;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.stage.CompileException;

public class BinaryParser extends AbstractParser {
    protected BinaryParser(State state) {
        super(state);
    }

    @Override
    protected boolean isValid(State state) {
        return state.queryCurrent(value -> value.is(Node.Type.Binary));
    }

    @Override
    protected State onExitImpl() {
        if (state.applyToScope(Scope::isLevel)) {
            return state.mapCurrent(Line::new);
        } else {
            return state;
        }
    }

    @Override
    protected State onParseImpl() throws CompileException {
        return super.onParseImpl();
    }
}
