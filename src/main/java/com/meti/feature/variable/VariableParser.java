package com.meti.feature.variable;

import com.meti.InterpretationError;
import com.meti.feature.Content;
import com.meti.feature.Node;
import com.meti.feature.Parser;
import com.meti.feature.attribute.Attribute;
import com.meti.safe.NativeString;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record VariableParser(State state, Node node) implements Parser {
    @Override
    public Option<Result<State, InterpretationError>> parse() {
        if (!node.is(Variable.Key.Id)) return None.apply();
        var value = node.apply(NativeString.from("value"))
                .flatMap(Attribute::asString)
                .unwrapOrPanic();

        return Some.apply(parseKey(value));
    }

    private Result<State, InterpretationError> parseKey(NativeString value) {
        return state.stack.apply(value).unwrapOrThrow(() -> {
                    var format = "'%s' is undefined.";
                    var message = format.formatted(value.internalValue());
                    return new InterpretationError(message);
                })
                .mapValue(node -> node
                        .apply(NativeString.from("value"))
                        .flatMap(Attribute::asString)
                        .unwrapOrPanic())
                .mapValue(Content::new)
                .mapValue(state::withValue);
    }
}