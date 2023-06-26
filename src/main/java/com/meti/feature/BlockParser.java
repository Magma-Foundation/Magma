package com.meti.feature;

import com.meti.InterpretationError;
import com.meti.Interpreter;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record BlockParser(State state1, NativeString slice) implements Parser {
    @Override
    public Option<Result<State, InterpretationError>> parse() {
        return Some.apply(slice.splitExcludingAtAll(";")
                .map(NativeString::strip)
                .filter(NativeString::isNonEmpty)
                .foldLeftResult(state1.empty().enter(), (state, nativeString) -> Interpreter.interpretStatement(state.withValue(nativeString)))
                .mapValue(State::exit));
    }
}