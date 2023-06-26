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
        return new AssignmentLexer(input).lex()
                .map(node -> new ParsingStage(state, node).parse());
    }
}
