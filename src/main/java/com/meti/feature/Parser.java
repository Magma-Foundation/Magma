package com.meti.feature;

import com.meti.InterpretationError;
import com.meti.Resolver;
import com.meti.safe.result.Err;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record Parser(State state, Assignment assignment) {
    Result<State, InterpretationError> parse() {
        return state().mapStack(stack -> stack.mapDefinition(assignment().name(), definition -> {
            var actualType = new Resolver(assignment().value()).resolve();
            if (definition.type().equalsTo(actualType)) {
                return Ok.apply(definition.withValue(assignment().value()));
            } else {
                var format = "Type mismatch of '%s'. Expected '%s' but was actually '%s'.";
                var message = format.formatted(assignment().name(), definition.type().internalValue(), actualType.internalValue());
                return new Err<>(new InterpretationError(message));
            }
        }));
    }
}