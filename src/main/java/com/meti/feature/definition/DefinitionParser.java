package com.meti.feature.definition;

import com.meti.InterpretationError;
import com.meti.Resolver;
import com.meti.feature.Node;
import com.meti.feature.Parser;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;
import com.meti.safe.result.Err;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record DefinitionParser(State state1, Node node) implements Parser {
    @Override
    public Option<Result<State, InterpretationError>> parse() {
        if (node.is(ImplicitDefinition.Key.Id)) {
            return new Some<>(state1().mapStack(stack -> {
                var typeOption = node().typeAsString();


                var value = node().valueAsString().unwrapOrPanic();
                var actualType = new Resolver(value).resolve();
                var newDefinition = typeOption.match(expectedType -> {
                    if (expectedType.equalsTo(actualType)) {
                        return Ok.<ExplicitDefinition, InterpretationError>apply(new ExplicitDefinition(node.nameAsString().unwrapOrPanic(),
                                expectedType,
                                value));
                    } else {
                        return Err.<ExplicitDefinition, InterpretationError>apply(new InterpretationError("Type mismatch."));
                    }
                }, () -> Ok.<ExplicitDefinition, InterpretationError>apply(new ExplicitDefinition(node.nameAsString().unwrapOrPanic(),
                        actualType,
                        value)));

                return newDefinition.mapValue(stack::define);
            }));
        } else {
            return None.apply();
        }
    }
}