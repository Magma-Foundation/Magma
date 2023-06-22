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
            return value1.firstIndexOfChar('=').match(equalsSeparator -> {
                return value1.firstIndexOfChar(':')
                        .<Result<Definition, InterpretationError>>match(typeSeparator -> {
                            var name = value1.slice("let ".length(), typeSeparator);
                            var type = value1.slice(typeSeparator + 1, equalsSeparator);
                            var value = value1.slice(equalsSeparator + 1, value1.length()).map(NativeString::strip);
                            return name.and(type)
                                    .and(value)
                                    .map(tuple -> new Definition(tuple.a().a(), tuple.a().b(), tuple.b()))
                                    .unwrapOrThrow(() -> new InterpretationError("Failed to parse name or value."));
                        }, () -> {
                            var name = value1.slice("let ".length(), equalsSeparator);
                            var value = value1.slice(equalsSeparator + 1, value1.length()).map(NativeString::strip);
                            return value.match(
                                            nativeString -> Ok.<NativeString, InterpretationError>of(resolveType(nativeString)),
                                            () -> new Err<NativeString, InterpretationError>(new InterpretationError("No value present.")))

                                    .mapValueToResult(nativeString -> name.and(value)
                                            .map(tuple -> new Definition(tuple.a(), nativeString, tuple.b()))
                                            .unwrapOrThrow(() -> new InterpretationError("Failed to parse name or value.")));
                        })
                        .mapValue(state::define);
            }, () -> new Err<>(new InterpretationError("No equals statement present.")));
        } else if (state.declarations.containsKey(value1)) {
            return Ok.of(state.mapValue(state.declarations::get));
        } else {
            return state.mapValueToResult(Interpreter::interpretValue);
        }
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