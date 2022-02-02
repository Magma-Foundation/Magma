package com.meti.app.compile.parse;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public abstract class UnitedParser implements Parser {
    @Override
    public Option<State> onEnter(State state) {
        return isValid(state) ? new Some<>(onEnterImpl(state)) : new None<>();
    }

    protected abstract boolean isValid(State state);

    protected abstract State onEnterImpl(State state);

    @Override
    public Option<State> onExit(State state) {
        return isValid(state) ? new Some<>(onExitImpl(state)) : new None<>();
    }

    protected abstract State onExitImpl(State state);
}
