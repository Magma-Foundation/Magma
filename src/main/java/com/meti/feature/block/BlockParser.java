package com.meti.feature.block;

import com.meti.InterpretationError;
import com.meti.Interpreter;
import com.meti.feature.attribute.Attribute;
import com.meti.feature.Node;
import com.meti.feature.Parser;
import com.meti.safe.NativeString;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;
import com.meti.safe.result.Result;
import com.meti.state.State;

public final class BlockParser implements Parser {
    private final State state1;
    private final Node node;

    public BlockParser(State state1, Node node) {
        this.state1 = state1;
        this.node = node;
    }

    @Override
    public Option<Result<State, InterpretationError>> parse() {
        if (node.is(Block.Key.Id)) {
            return Some.apply(node.apply(NativeString.from("lines")).flatMap(Attribute::asListOfNodes)
                    .unwrapOrPanic()
                    .iter()
                    .foldLeftResult(state1.empty().enter(), (state, line) -> Interpreter.interpretStatement(state.withValue(line)))
                    .mapValue(State::exit));
        } else {
            return None.apply();
        }
    }
}