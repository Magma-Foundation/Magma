package com.meti;

import java.util.Arrays;
import java.util.HashMap;

public record Interpreter(String input) {

    private static State interpretStatement(PresentState state) {
        var value1 = state.value;
        var declarations = state.declarations;
        if (value1.startsWith("let ")) {
            var name = value1.substring("let ".length(), value1.indexOf('=')).strip();
            var value = value1.substring(value1.indexOf('=') + 1).strip();
            declarations.put(name, value);
            return new State(declarations);
        } else if (declarations.containsKey(value1)) {
            return new PresentState(declarations.get(value1), declarations);
        } else {
            return new PresentState(interpretValue(state.value), declarations);
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
        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            var result = interpretStatement(new PresentState(line, declarations));
            if (i == lines.size() - 1) {
                var option = result.findValue();
                if (option.isPresent()) {
                    return option.unwrapOrPanic();
                }
            }
        }

        return "";
    }
}