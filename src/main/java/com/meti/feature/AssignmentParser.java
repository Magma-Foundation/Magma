package com.meti.feature;

import com.meti.InterpretationError;
import com.meti.Resolver;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;
import com.meti.safe.result.Err;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record AssignmentParser(State state, Node node) implements Parser {
    @Override
    public Option<Result<State, InterpretationError>> parse() {
        if (node.is(Assignment.Key.Id)) {
            return Some.apply(state.mapStack(stack -> {
                var name = node.nameAsString().unwrapOrPanic();
                var value = node.valueAsString().unwrapOrPanic();

                return stack.mapDefinition(name, definition -> {
                    var actualType = new Resolver(value).resolve();
                    if (definition.type().equalsTo(actualType)) {
                        return Ok.apply(definition.withValue(value));
                    } else {
                        var format = "Type mismatch of '%s'. Expected '%s' but was actually '%s'.";
                        var message = format.formatted(this.node().nameAsString().unwrapOrPanic(), definition.type().internalValue(), actualType.internalValue());
                        return new Err<>(new InterpretationError(message));
                    }
                });
            }));
        } else {
            return None.apply();
        }
    }
}