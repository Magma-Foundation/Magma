package com.meti.feature.variable;

import com.meti.InterpretationError;
import com.meti.feature.Content;
import com.meti.feature.Node;
import com.meti.feature.Parser;
import com.meti.safe.option.Option;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;
import com.meti.state.Definition;
import com.meti.state.State;

public record VariableParser(State state, Node variable) implements Parser {
    @Override
    public Option<Result<State, InterpretationError>> parse() {
        return state.stack.apply(variable.valueAsString().unwrapOrPanic())
                .map(Definition::value)
                .map(Content::new)
                .map(state::withValue)
                .map(Ok::apply);
    }
}