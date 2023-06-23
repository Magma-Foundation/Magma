package com.meti;

import java.util.Arrays;

public final class Interpreter {
    private final NativeString input;

    public Interpreter(NativeString input) {
        this.input = input;
    }

    private static Result<State, InterpretationError> interpretStatement(PresentState state) {
        var input = state.value;
        return Iterators.of(new DefinitionActor(state, input),
                        new VariableActor(state, input),
                        new NumberActor(state, input))
                .map(Actor::act)
                .flatMap(Iterators::fromOption)
                .head()
                .unwrapOrElse(new Err<>(new InterpretationError("Unknown value: " + input.internalValue())));
    }

    Result<NativeString, InterpretationError> interpret() {
        var lines = Arrays.stream(input.internalValue().split(";"))
                .map(String::strip)
                .filter(line -> !line.isEmpty())
                .map(NativeString::new)
                .toList();

        return NativeIterators.fromList(lines)
                .foldLeftResult(EmptyState.create(), (previous, line) -> interpretStatement(previous.withValue(line)))
                .mapValue(internal -> internal.findValue().unwrapOrElse(NativeString.from("")));
    }
}