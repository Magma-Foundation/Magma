package com.meti.feature.definition;

import com.meti.InterpretationError;
import com.meti.Resolver;
import com.meti.feature.Actor;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.result.Err;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;
import com.meti.safe.result.Results;
import com.meti.state.State;

public record DefinitionActor(State state, NativeString input) implements Actor {
    private static Option<Result<Definition, InterpretationError>> lex(NativeString range) {
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
                    return Err.<Definition, InterpretationError>apply(new InterpretationError("Type mismatch."));
                }
            }).unwrapOrElseGet(() -> Ok.apply(new Definition(definition, expectedType, value)));
        });
    }

    @Override
    public Option<Result<State, InterpretationError>> act() {
        // let x : u64 = 10
        // x : u64 = 10
        return input.firstIndexAfterSlice(NativeString.from("let"))
                .map(input::sliceFrom)
                .flatMap(DefinitionActor::lex)
                .map(result -> Results.invert(result.mapValue((Definition result1) -> new DefinitionParser(state, result1).parse())))
                .flatMap(value -> value)
                .map(Results::flatten);
    }
}