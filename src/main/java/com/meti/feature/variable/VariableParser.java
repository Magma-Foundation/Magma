package com.meti.feature.variable;

import com.meti.InterpretationError;
import com.meti.feature.Content;
import com.meti.feature.Node;
import com.meti.feature.Parser;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record VariableParser(State state, Node node) implements Parser {
    @Override
    public Option<Result<State, InterpretationError>> parse() {
        if (node.is(Variable.Key.Id)) {
            return state.stack.apply(node.valueAsString().unwrapOrPanic())
                    .map(node -> node.valueAsString().unwrapOrPanic())
                    .map(Content::new)
                    .map(state::withValue)
                    .map(Ok::apply);
        } else {
            return None.apply();
        }
    }
}