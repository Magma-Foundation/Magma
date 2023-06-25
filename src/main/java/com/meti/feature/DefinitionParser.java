package com.meti.feature;

import com.meti.InterpretationError;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;
import com.meti.state.Definition;
import com.meti.state.State;

public record DefinitionParser(State state1, Node result) implements Parser {
    @Override
    public Option<Result<State, InterpretationError>> parse() {
        return new Some<>(Ok.apply(state1().mapStack(stack -> {
            var newDefinition = new Definition(result().nameAsString().unwrapOrPanic(),
                    result().typeAsString().unwrapOrPanic(),
                    result().valueAsString().unwrapOrPanic());

            return stack.define(newDefinition);
        })));
    }
}