package com.meti.feature.integer;

import com.meti.InterpretationError;
import com.meti.feature.Actor;
import com.meti.feature.ParsingStage;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record NumberActor(State state, NativeString input) implements Actor {
    @Override
    public Option<Result<State, InterpretationError>> act() {
        return new NumberLexer(this.input).lex()
                .map(node -> new ParsingStage(state, node))
                .map(ParsingStage::parse);
    }

}