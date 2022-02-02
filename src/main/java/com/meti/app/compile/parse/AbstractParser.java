package com.meti.app.compile.parse;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.stage.CompileException;

public abstract class AbstractParser implements Parser {
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
    public Option<State> onExit() {
        return isValid() ? new Some<>(onExitImpl()) : new None<>();
    }

    protected State onExitImpl() {
        return state;
    }

    @Override
    public Option<State> onParse() throws CompileException {
        return isValid() ? new Some<>(onParseImpl()) : new None<>();
    }

    protected State onParseImpl() throws CompileException {
        return state;
    }
}
