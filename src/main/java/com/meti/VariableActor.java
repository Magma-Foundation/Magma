package com.meti;

public record VariableActor(State state, NativeString input) implements Actor {
    @Override
    public Option<Result<State, InterpretationError>> act() {
        return state.stack.apply(input)
                .map(Definition::value)
                .map(state::withValue)
                .map(Ok::apply);
    }
}