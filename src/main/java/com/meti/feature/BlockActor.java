package com.meti.feature;

import com.meti.InterpretationError;
import com.meti.Interpreter;
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
                .map(this::actOnSlice);
    }

    private Result<State, InterpretationError> actOnSlice(NativeString slice) {
        return slice.splitExcludingAtAll(";")
                .map(NativeString::strip)
                .filter(NativeString::isNonEmpty)
                .foldLeftResult(state.empty().enter(), (state, nativeString) -> Interpreter.interpretStatement(state.withValue(nativeString)))
                .mapValue(State::exit);
    }
}
