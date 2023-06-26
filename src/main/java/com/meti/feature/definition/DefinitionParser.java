package com.meti.feature.definition;

import com.meti.InterpretationError;
import com.meti.feature.Node;
import com.meti.feature.Parser;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record DefinitionParser(State state1, Node node) implements Parser {
    @Override
    public Option<Result<State, InterpretationError>> parse() {
        if (node.is(Definition.Key.Id)) {
            return new Some<>(state1().mapStack(stack -> {
                var newDefinition = new Definition(node().nameAsString().unwrapOrPanic(),
                        node().typeAsString().unwrapOrPanic(),
                        node().valueAsString().unwrapOrPanic());

                return Ok.apply(stack.define(newDefinition));
            }));
        } else {
            return None.apply();
        }
    }
}