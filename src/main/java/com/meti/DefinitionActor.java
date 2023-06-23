package com.meti;

public record DefinitionActor(State state, NativeString input) implements Actor {
    @Override
    public Option<Result<State, InterpretationError>> act() {
        // let x : u64 = 10
        return input.firstIndexAfterSlice(NativeString.from("let"))
                .map(input::sliceFrom)
                .flatMap(range -> {
                    // x : u64 = 10
                    return range.firstIndexOfChar('=').map(valueSeparator -> {
                        var split = range.splitExcludingAt(valueSeparator);
                        var definition = split.left().strip();
                        var value = split.right().strip();
                        var expectedType = new Resolver(value).resolve();

                        return definition.firstIndexOfChar(':').map(definition::splitExcludingAt).map(tuple -> {
                            var name = tuple.left().strip();
                            var actualType = tuple.right().strip();

                            if (expectedType.equalsTo(actualType)) {
                                return Ok.<Definition, InterpretationError>apply(new Definition(name, actualType, value));
                            } else {
                                return new Err<Definition, InterpretationError>(new InterpretationError("Type mismatch."));
                            }
                        }).unwrapOrElseGet(() -> Ok.apply(new Definition(definition, expectedType, value)));
                    });
                })
                .map(result -> state.mapStack(stack -> result.mapValue(stack::define)));
    }
}