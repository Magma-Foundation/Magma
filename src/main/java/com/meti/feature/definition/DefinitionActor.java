package com.meti.feature.definition;

import com.meti.InterpretationError;
import com.meti.feature.Actor;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record DefinitionActor(State state, NativeString input) implements Actor {

    @Override
    public Option<Result<State, InterpretationError>> act() {
        // let x : u64 = 10
        // x : u64 = 10
        return new DefinitionLexer(input).lex()
                .map(result -> new DefinitionParser(state, result).parse())
                .flatMap(value -> value);
    }

}