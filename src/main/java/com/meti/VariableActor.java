package com.meti;

public record VariableActor(State state, NativeString input) implements Actor {
    @Override
    public Option<Result<State, InterpretationError>> act() {
        if (state.stack.definitions().unwrap().containsKey(input())) {
            return new Some<>(Ok.apply(state.mapValue(state.stack.definitions().unwrap()::get)));
        }
        return new None<>();
    }
}