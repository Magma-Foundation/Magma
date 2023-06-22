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
            return value1.firstIndexOfChar('=').match(equator -> {
                var name = value1.slice("let ".length(), equator).map(NativeString::strip);
                var value = value1.slice(equator + 1, value1.length()).map(NativeString::strip);

                return name.and(value)
                        .map(tuple -> state.define(tuple.a(), tuple.b()))
                        .unwrapOrThrow(() -> new InterpretationError("Failed to parse name or value."));
            }, () -> new Err<>(new InterpretationError("No equals statement present.")));
        } else if (state.declarations.containsKey(value1)) {
            return Ok.of(state.mapValue(state.declarations::get));
        } else {
            return state.mapValueToResult(Interpreter::interpretValue);
        }
    }

    private static Result<NativeString, InterpretationError> interpretValue(NativeString input) {
        try {
            var withoutSuffix = Iterators.fromRange(0, input.length()).zip(input.iter())
                    .map(tuple -> {
                        if (Character.isLetter(tuple.b())) {
                            return new Some<>(tuple.a());
                        } else {
                            return new None<Integer>();
                        }
                    })
                    .flatMap(Iterators::fromOption)
                    .head()
                    .match(index -> input.slice(0, index).unwrapOrElse(input), () -> input);
            ;
            return Ok.of(NativeString.from(String.valueOf(Integer.parseInt(withoutSuffix.strip().internalValue()))));
        } catch (NumberFormatException e) {
            return new Err<>(new InterpretationError("Unknown value: " + input.internalValue()));
        }
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