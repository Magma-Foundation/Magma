package com.meti;

import com.meti.feature.*;
import com.meti.safe.NativeString;
import com.meti.safe.iter.Iterators;
import com.meti.safe.iter.NativeIterators;
import com.meti.safe.result.Err;
import com.meti.safe.result.Result;
import com.meti.state.EmptyState;
import com.meti.state.PresentState;
import com.meti.state.State;

import java.util.Arrays;

public final class Interpreter {
    private final NativeString input;

    public Interpreter(NativeString input) {
        this.input = input;
    }

    public static Result<State, InterpretationError> interpretStatement(PresentState state) {
        var input = state.value;
        return Iterators.of(
                        new BlockActor(state, input),
                        new DefinitionActor(state, input),
                        new AssignmentActor(state, input),
                        new VariableActor(state, input),
                        new NumberActor(state, input))
                .map(Actor::act)
                .flatMap(Iterators::fromOption)
                .head()
                .unwrapOrElse(new Err<>(new InterpretationError("Unknown value: " + input.internalValue())));
    }

    public Result<NativeString, InterpretationError> interpret() {
        return interpret(EmptyState.create());
    }

    public Result<NativeString, InterpretationError> interpret(State state) {
        var lines = Arrays.stream(input.internalValue().split(";"))
                .map(String::strip)
                .filter(line -> !line.isEmpty())
                .map(NativeString::new)
                .toList();

        return NativeIterators.fromList(lines)
                .foldLeftResult(state, (previous, line) -> interpretStatement(previous.withValue(line)))
                .mapValue(internal -> internal.findValue().unwrapOrElse(NativeString.from("")));
    }
}