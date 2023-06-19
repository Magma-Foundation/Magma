package com.meti;

import java.util.Arrays;
import java.util.HashMap;

public record Interpreter(String input) {

    private static State interpretStatement(PresentState state) {
        var value1 = state.value;
        if (value1.startsWith("let ")) {
            var name = value1.substring("let ".length(), value1.indexOf('=')).strip();
            var value = value1.substring(value1.indexOf('=') + 1).strip();
            return state.define(name, value);
        } else if (state.declarations.containsKey(value1)) {
            return state.mapValue(state.declarations::get);
        } else {
            return state.mapValue(Interpreter::interpretValue);
        }
    }

    private static String interpretValue(String input) {
        try {
            return String.valueOf(Integer.parseInt(input.strip()));
        } catch (NumberFormatException e) {
            return "";
        }
    }

    String interpret() {
        var lines = Arrays.stream(input().split(";"))
                .map(String::strip)
                .filter(line -> !line.isEmpty())
                .toList();

        var declarations = new HashMap<String, String>();
        var state = new State();
        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            var withValue = state.withValue(line);
            var interpreted = interpretStatement(withValue);
            if (i == lines.size() - 1) {
                state = interpreted;
                var option = interpreted.findValue();
                if (option.isPresent()) {
                    return option.unwrapOrPanic();
                }
            }
        }

        return "";
    }
}