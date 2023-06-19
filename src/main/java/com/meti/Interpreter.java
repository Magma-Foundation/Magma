package com.meti;

import java.util.Arrays;

public final class Interpreter {
    private final NativeString input;

    public Interpreter(NativeString input) {
        this.input = input;
    }

    private static Result<State, InterpretationError> interpretStatement(PresentState state) {
        var value1 = state.value;
        if (value1.startsWith(NativeString.from("let "))) {
            var name = value1.firstIndexOfChar('=')
                    .flatMap(index -> value1.slice("let ".length(), index))
                    .map(NativeString::strip);

            var value = value1.firstIndexOfChar('=')
                    .flatMap(index -> value1.slice(index + 1, value1.length()))
                    .map(NativeString::strip);

            return name.and(value)
                    .map(tuple -> state.define(tuple.a(), tuple.b()))
                    .unwrapOrThrow(() -> new InterpretationError("Failed to parse name or value."));

        } else if (state.declarations.containsKey(value1)) {
            return Ok.of(state.mapValue(state.declarations::get));
        } else {
            return Ok.of(state.mapValue(Interpreter::interpretValue));
        }
    }

    private static NativeString interpretValue(NativeString input) {
        try {
            return NativeString.from(String.valueOf(Integer.parseInt(input.strip().unwrap())));
        } catch (NumberFormatException e) {
            return NativeString.from("");
        }
    }

    Result<NativeString, InterpretationError> interpret1() {
        var lines = Arrays.stream(input.unwrap().split(";"))
                .map(String::strip)
                .filter(line -> !line.isEmpty())
                .map(NativeString::new)
                .toList();

        return NativeIterators.fromList(lines)
                .foldLeftResult(EmptyState.create(), (previous, line) -> interpretStatement(previous.withValue(line)))
                .mapValue(internal -> internal.findValue().unwrapOrElse(NativeString.from("")));
    }
}