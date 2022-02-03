package com.meti.app.compile.parse;

import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.feature.util.Line;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.stage.CompileException;

public class BinaryParser extends AbstractParser {
    protected BinaryParser(State state) {
        super(state);
    }

    @Override
    protected boolean isValid() {
        return state.applyToCurrent(value -> value.is(Node.Category.Binary));
    }

    @Override
    protected State onExitImpl() {
        if (state.applyToScope(Scope::isLevel)) {
            return state.mapCurrent(Line::new);
        } else {
            return state;
        }
    }

    private State modifyBeforeASTImpl2() throws CompileException {
        return state;
    }

    @Override
    protected Option<State> modifyAfterASTImpl() {
        return new Some<>(modifyAfterASTImpl2());
    }

    private State modifyAfterASTImpl2() {
        return state;
    }

    @Override
    protected Option<State> modifyBeforeASTImpl() throws CompileException {
        return new Some<>(modifyBeforeASTImpl2());
    }
}
