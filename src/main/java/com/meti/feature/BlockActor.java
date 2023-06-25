package com.meti.feature;

import com.meti.InterpretationError;
import com.meti.Interpreter;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.result.Result;
import com.meti.state.State;

import java.util.function.BiFunction;

public record BlockActor(State state, NativeString input) implements Actor {
    @Override
    public Option<Result<State, InterpretationError>> act() {
        return input.firstIndexOfChar('{')
                .flatMap(NativeString.Index::next)
                .flatMap(index -> input.lastIndexOfChar('}').flatMap(index::to))
                .map(input::slice)
                .map(slice -> {
                    return slice.splitExcludingAtAll(";")
                            .map(NativeString::strip)
                            .filter(NativeString::isNonEmpty)
                            .foldLeftResult(state.empty(), (state, nativeString) -> Interpreter.interpretStatement(state.withValue(nativeString)));
                });
    }
}
