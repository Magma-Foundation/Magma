package com.meti.feature.definition;

import com.meti.InterpretationError;
import com.meti.Resolver;
import com.meti.feature.Actor;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record DefinitionActor(State state, NativeString input) implements Actor {
    private static Option<ImplicitDefinition> lex(NativeString range) {
        return range.firstIndexOfChar('=').map(valueSeparator -> {
            var split = range.splitExcludingAt(valueSeparator);
            var definition = split.left().strip();
            var value = split.right().strip();
            var expectedType = new Resolver(value).resolve();

            return definition.firstIndexOfChar(':').map(definition::splitExcludingAt).<ImplicitDefinition>map(tuple -> {
                var name = tuple.left().strip();
                var actualType = tuple.right().strip();

                return new ExplicitDefinition(name, actualType, value);
            }).unwrapOrElseGet(() -> new ImplicitDefinition(definition, value));
        });
    }

    @Override
    public Option<Result<State, InterpretationError>> act() {
        // let x : u64 = 10
        // x : u64 = 10
        return input.firstIndexAfterSlice(NativeString.from("let"))
                .map(input::sliceFrom)
                .flatMap(DefinitionActor::lex)
                .map(result -> new DefinitionParser(state, result).parse())
                .flatMap(value -> value);
    }
}