package com.meti;

import java.util.function.Function;

public abstract class State {
    public final Stack stack;

    public State(Stack stack) {
        this.stack = stack;
    }

    public Result<State, InterpretationError> mapStack(Function<Stack, Result<Stack, InterpretationError>> mapper) {
        return mapper.apply(stack).mapValue(this::copy);
    }

    protected abstract State copy(Stack stack);

    abstract PresentState mapValue(Function<NativeString, NativeString> mapper);

    public abstract Option<NativeString> findValue();

    public abstract PresentState withValue(NativeString value);
}
