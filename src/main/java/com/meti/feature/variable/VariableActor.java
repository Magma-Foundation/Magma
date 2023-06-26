package com.meti.feature.variable;

import com.meti.InterpretationError;
import com.meti.feature.Actor;
import com.meti.feature.Content;
import com.meti.feature.Parser;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;
import com.meti.state.Definition;
import com.meti.state.State;

public record VariableActor(State state, NativeString input) implements Actor, Parser {
    @Override
    public Option<Result<State, InterpretationError>> parse() {
        return act();
    }

    @Override
    public Option<Result<State, InterpretationError>> act() {
        return state.stack.apply(input)
                .map(Definition::value)
                .map(value -> state.withValue(new Content(value)))
                .map(Ok::apply);
    }
}