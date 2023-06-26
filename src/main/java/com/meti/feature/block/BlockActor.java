package com.meti.feature.block;

import com.meti.InterpretationError;
import com.meti.feature.Actor;
import com.meti.feature.ParsingStage;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record BlockActor(State state, NativeString input) implements Actor {
    @Override
    public Option<Result<State, InterpretationError>> act() {
        return new BlockLexer(input).lex().map(node -> {
                    return new ParsingStage(state, node).parse();
                });
    }

}
