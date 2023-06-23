package com.meti;

public record VariableActor(State state, NativeString input) implements Actor {
    @Override
    public Option<Result<State, InterpretationError>> act() {
        if (state.declarations.containsKey(input())) {
            return new Some<>(Ok.of(state.mapValue(state.declarations::get)));
        }
        return new None<>();
    }
}