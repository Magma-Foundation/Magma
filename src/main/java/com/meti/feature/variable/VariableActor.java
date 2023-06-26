package com.meti.feature.variable;

import com.meti.InterpretationError;
import com.meti.feature.Actor;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record VariableActor(State state, NativeString input) implements Actor {
    @Override
    public Option<Result<State, InterpretationError>> act() {
        return new VariableParser(state, new Variable(input)).parse();
    }
}