package com.meti.feature.assign;

import com.meti.InterpretationError;
import com.meti.feature.Actor;
import com.meti.feature.ParsingStage;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record AssignmentActor(State state, NativeString input) implements Actor {
    @Override
    public Option<Result<State, InterpretationError>> act() {
        return input.firstIndexOfChar('=').map(equals -> {
            var split = input.splitExcludingAt(equals);
            var name = split.left();
            var value = split.right();
            return new ParsingStage(state, new Assignment(name, value)).parse();
        });
    }
}