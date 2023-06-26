package com.meti.feature;

import com.meti.InterpretationError;
import com.meti.safe.Index;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record BlockActor(State state, NativeString input) implements Actor {
    @Override
    public Option<Result<State, InterpretationError>> act() {
        return input.firstIndexOfChar('{')
                .flatMap(Index::next)
                .flatMap(index -> input.lastIndexOfChar('}').flatMap(index::to))
                .map(input::slice)
                .map(slice -> new BlockParser(state, slice).parse().unwrapOrPanic());
    }
}
