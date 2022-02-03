package com.meti.app.compile.parse;

import com.meti.app.compile.feature.util.Line;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.stage.CompileException;

public class BinaryParser extends AbstractParser {
    protected BinaryParser(State state) {
        super(state);
    }

    @Override
    protected boolean isValid() {
        return state.queryCurrent(value -> value.is(Node.Role.Binary));
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
    protected State modifyBeforeASTImpl() throws CompileException {
        return super.modifyBeforeASTImpl();
    }
}
