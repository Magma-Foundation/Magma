package com.meti;

import static com.meti.NativeResults.*;

public record DefinitionActor(State state, NativeString input) implements Actor {
    Result<Definition, InterpretationError> interpretDefinition(int equalsSeparator) {
        return input.firstIndexOfChar(':').match(typeSeparator -> $throwResult(() -> {
            var name = $(input.slice("let ".length(), typeSeparator).unwrapOrThrow(() -> new InterpretationError("No name present.")));
            var expectedType = $(input.slice(typeSeparator + 1, equalsSeparator).unwrapOrThrow(() -> new InterpretationError("No type present.")));
            var value = $(input.slice(equalsSeparator + 1, input.length()).unwrapOrThrow(() -> new InterpretationError("No value present.")));
            var actualType = new Resolver(value).resolveType();
            if (expectedType.equalsTo(actualType)) {
                return Ok.of(new Definition(name, expectedType, value));
            } else {
                return new Err<>(new InterpretationError("Invalid type: " + actualType.internalValue()));
            }
        }), () -> $throw(() -> {
            var name = $(input.slice("let ".length(), equalsSeparator).unwrapOrThrow(() -> new InterpretationError("No name present."))).strip();
            var value = $(input.slice(equalsSeparator + 1, input.length()).unwrapOrThrow(() -> new InterpretationError("No value present."))).strip();
            var type = new Resolver(value).resolveType();
            return new Definition(name, type, value);
        }));
    }

    @Override
    public Option<Result<State, InterpretationError>> act() {
        if (input.startsWith(NativeString.from("let "))) {
            var match = input.firstIndexOfChar('=').match(this::interpretDefinition,
                    () -> new Err<Definition, InterpretationError>(new InterpretationError("No equals sign present.")));
            return new Some<>(match.mapValue(state::define));
        } else {
            return new None<>();
        }
    }
}