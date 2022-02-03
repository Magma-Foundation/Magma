package com.meti.app.compile.parse;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.stage.CompileException;

public abstract class AbstractParser implements Modifier {
    protected final State state;

    protected AbstractParser(State state) {
        this.state = state;
    }

    @Override
    public Option<State> onEnter() {
        return isValid() ? new Some<>(onEnterImpl()) : new None<>();
    }

    protected abstract boolean isValid();

    protected State onEnterImpl() {
        return state;
    }

    @Override
    public Option<State> onExit() throws CompileException {
        return isValid() ? new Some<>(onExitImpl()) : new None<>();
    }

    protected State onExitImpl() throws CompileException {
        return state;
    }

    @Override
    public Option<State> modifyBeforeAST() throws CompileException {
        return isValid() ? new Some<>(modifyBeforeASTImpl().orElse(state)) : new None<>();
    }

    protected Option<State> modifyBeforeASTImpl() throws CompileException {
        return new None<>();
    }

    @Override
    public Option<State> modifyAfterAST() {
        return isValid() ? new Some<>(modifyAfterASTImpl().orElse(state)) : new None<>();
    }

    protected Option<State> modifyAfterASTImpl() {
        return new None<>();
    }
}
