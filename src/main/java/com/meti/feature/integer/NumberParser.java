package com.meti.feature.integer;

import com.meti.InterpretationError;
import com.meti.feature.Content;
import com.meti.feature.Node;
import com.meti.feature.Parser;
import com.meti.safe.NativeString;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record NumberParser(State state, Node root) implements Parser {
    @Override
    public Option<Result<State, InterpretationError>> parse() {
        if (root.is(IntNode.Key.Id)) {
            var integer = root.valueAsInt().unwrapOrPanic();
            var from = NativeString.from(String.valueOf(integer));
            return Some.apply(Ok.apply(state.withValue(new Content(from))));
        } else {
            return None.apply();
        }
    }
}