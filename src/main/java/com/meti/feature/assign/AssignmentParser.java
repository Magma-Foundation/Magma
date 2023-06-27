package com.meti.feature.assign;

import com.meti.InterpretationError;
import com.meti.Resolver;
import com.meti.feature.attribute.Attribute;
import com.meti.feature.Node;
import com.meti.feature.Parser;
import com.meti.safe.NativeString;
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
                var name = node.apply(NativeString.from("name")).flatMap(Attribute::asString).unwrapOrPanic();
                var value = node.apply(NativeString.from("value")).flatMap(Attribute::asString).unwrapOrPanic();

                return stack.mapDefinition(name, definition -> {
                    var actualType = new Resolver(value).resolve();
                    if (definition.apply(NativeString.from("type")).flatMap(Attribute::asString).unwrapOrPanic().equalsTo(actualType)) {
                        return Ok.apply(definition.withValue(value));
                    } else {
                        var format = "Type mismatch of '%s'. Expected '%s' but was actually '%s'.";
                        var message = format.formatted(this.node().apply(NativeString.from("name")).flatMap(Attribute::asString).unwrapOrPanic(), definition.apply(NativeString.from("type")).flatMap(Attribute::asString).unwrapOrPanic().internalValue(), actualType.internalValue());
                        return Err.apply(new InterpretationError(message));
                    }
                });
            }));
        } else {
            return None.apply();
        }
    }
}