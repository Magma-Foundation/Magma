package com.meti.feature;

import com.meti.InterpretationError;
import com.meti.Resolver;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.result.Err;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record AssignmentActor(State state, NativeString input) implements Actor {
    @Override
    public Option<Result<State, InterpretationError>> act() {
        return input.firstIndexOfChar('=').map(equals -> {
            var split = input.splitExcludingAt(equals);
            var name = split.left();
            var value = split.right();
            return state.mapStack(stack -> stack.mapDefinition(name, definition -> {
                var actualType = new Resolver(value).resolve();
                if (definition.type().equalsTo(actualType)) {
                    return Ok.apply(definition.withValue(value));
                } else {
                    var format = "Type mismatch of '%s'. Expected '%s' but was actually '%s'.";
                    var message = format.formatted(name, definition.type().internalValue(), actualType.internalValue());
                    return new Err<>(new InterpretationError(message));
                }
            }));
        });
    }
}
