package com.meti.feature.block;

import com.meti.InterpretationError;
import com.meti.Interpreter;
import com.meti.feature.Parser;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;
import com.meti.safe.result.Result;
import com.meti.state.State;

public final class BlockParser implements Parser {
    private final State state1;
    private final Block node;

    public BlockParser(State state1, Block node) {
        this.state1 = state1;
        this.node = node;
    }

    @Override
    public Option<Result<State, InterpretationError>> parse() {
        return Some.apply(node.lines()
                .unwrapOrPanic()
                .iter()
                .foldLeftResult(state1.empty().enter(), (state, line) -> Interpreter.interpretStatement(state.withValue(line)))
                .mapValue(State::exit));
    }
}