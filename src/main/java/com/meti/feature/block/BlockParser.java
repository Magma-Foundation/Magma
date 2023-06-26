package com.meti.feature.block;

import com.meti.InterpretationError;
import com.meti.Interpreter;
import com.meti.feature.Content;
import com.meti.feature.Parser;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;
import com.meti.safe.result.Result;
import com.meti.state.State;

import java.util.Objects;

public final class BlockParser implements Parser {
    private final State state1;
    private final NativeString slice;

    public BlockParser(State state1, NativeString slice) {
        this.state1 = state1;
        this.slice = slice;
    }

    @Override
    public Option<Result<State, InterpretationError>> parse() {
        return Some.apply(slice.splitExcludingAtAll(";")
                .map(NativeString::strip)
                .filter(NativeString::isNonEmpty)
                .foldLeftResult(state1.empty().enter(), (state, nativeString) -> Interpreter.interpretStatement(state.withValue(new Content(nativeString))))
                .mapValue(State::exit));
    }
}