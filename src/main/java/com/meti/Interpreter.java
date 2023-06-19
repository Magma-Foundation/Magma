package com.meti;

import java.util.Arrays;

public final class Interpreter {
    private final NativeString input;

    public Interpreter(NativeString input) {
        this.input = input;
    }

    private static State interpretStatement(PresentState state) {
        var value1 = state.value;
        if (value1.startsWith(new NativeString("let "))) {
            var name = value1.slice("let ".length(), value1.firstIndexOfChar('=').unwrapOrElse(-1)).strip();
            var value = value1.slice(value1.firstIndexOfChar('=').unwrapOrElse(-1) + 1, value1.length()).strip();
            return state.define(name, value);
        } else if (state.declarations.containsKey(value1)) {
            return state.mapValue(state.declarations::get);
        } else {
            return state.mapValue(Interpreter::interpretValue);
        }
    }

    private static NativeString interpretValue(NativeString input) {
        try {
            return new NativeString(String.valueOf(Integer.parseInt(input.strip().unwrap())));
        } catch (NumberFormatException e) {
            return new NativeString("");
        }
    }

    NativeString interpret1() {
        var lines = Arrays.stream(input.unwrap().split(";"))
                .map(String::strip)
                .filter(line -> !line.isEmpty())
                .map(NativeString::new)
                .toList();

        return lines.stream()
                .reduce(EmptyState.create(), (previous, line) -> interpretStatement(previous.withValue(line)), (previous, next) -> next)
                .findValue()
                .unwrapOrElse(new NativeString(""));
    }
}