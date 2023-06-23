package com.meti.feature;

import com.meti.InterpretationError;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;
import com.meti.state.Definition;
import com.meti.state.State;

public record VariableActor(State state, NativeString input) implements Actor {
    @Override
    public Option<Result<State, InterpretationError>> act() {
        return state.stack.apply(input)
                .map(Definition::value)
                .map(state::withValue)
                .map(Ok::apply);
    }
}