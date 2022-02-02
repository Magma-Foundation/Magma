package com.meti.app.compile.parse;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.stage.CompileException;

public abstract class UnitedParser implements Parser {
    protected final State state;

    protected UnitedParser(State state) {
        this.state = state;
    }

    @Override
    public Option<State> onEnter() throws CompileException {
        return isValid(state) ? new Some<>(onEnterImpl()) : new None<>();
    }

    protected abstract boolean isValid(State state);

    protected State onEnterImpl() throws CompileException {
        return state;
    }

    @Override
    public Option<State> onExit() throws CompileException {
        return isValid(state) ? new Some<>(onExitImpl()) : new None<>();
    }

    @Override
    public Option<State> onParse() throws CompileException {
        return isValid(state) ? new Some<>(onParseImpl()) : new None<>();
    }

    protected State onParseImpl() throws CompileException {
        return state;
    }

    protected State onExitImpl() throws CompileException {
        return state;
    }
}
