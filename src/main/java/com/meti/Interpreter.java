package com.meti;

import java.util.Arrays;

import static com.meti.NativeResults.*;

public final class Interpreter {
    private final NativeString input;

    public Interpreter(NativeString input) {
        this.input = input;
    }

    private static Result<State, InterpretationError> interpretStatement(PresentState state) {
        var input = state.value;
        if (input.startsWith(NativeString.from("let "))) {
            return input.firstIndexOfChar('=').match(
                            equalsSeparator -> interpretDefinition(input, equalsSeparator),
                            () -> new Err<Definition, InterpretationError>(new InterpretationError("No equals sign present.")))
                    .mapValue(state::define);
        } else if (state.declarations.containsKey(input)) {
            return Ok.of(state.mapValue(state.declarations::get));
        } else {
            return state.mapValueToResult(Interpreter::interpretValue);
        }
    }

    private static Result<Definition, InterpretationError> interpretDefinition(NativeString input, int equalsSeparator) {
        return input.firstIndexOfChar(':').match(typeSeparator -> $throwResult(() -> {
            var name = $(input.slice("let ".length(), typeSeparator).unwrapOrThrow(() -> new InterpretationError("No name present.")));
            var expectedType = $(input.slice(typeSeparator + 1, equalsSeparator).unwrapOrThrow(() -> new InterpretationError("No type present.")));
            var value = $(input.slice(equalsSeparator + 1, input.length()).unwrapOrThrow(() -> new InterpretationError("No value present.")));
            var actualType = resolveType(value);
            if (expectedType.equalsTo(actualType)) {
                return Ok.of(new Definition(name, expectedType, value));
            } else {
                return new Err<>(new InterpretationError("Invalid type: " + actualType.internalValue()));
            }
        }), () -> $throw(() -> {
            var name = $(input.slice("let ".length(), equalsSeparator).unwrapOrThrow(() -> new InterpretationError("No name present."))).strip();
            var value = $(input.slice(equalsSeparator + 1, input.length()).unwrapOrThrow(() -> new InterpretationError("No value present."))).strip();
            var type = resolveType(value);
            return new Definition(name, type, value);
        }));
    }

    private static NativeString resolveType(NativeString input) {
        return findSuffixStart(input).match(index -> input.slice(index, input.length()).unwrapOrElse(input), () -> input);
    }

    private static Option<Integer> findSuffixStart(NativeString input) {
        return Iterators.fromRange(0, input.length()).zip(input.iter())
                .map(tuple -> Character.isLetter(tuple.b()) ? new Some<>(tuple.a()) : new None<Integer>())
                .flatMap(Iterators::fromOption)
                .head();
    }

    private static Result<NativeString, InterpretationError> interpretValue(NativeString input) {
        try {
            var withoutSuffix = findSuffixStart(input).match(index -> input.slice(0, index).unwrapOrElse(input), () -> input);
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