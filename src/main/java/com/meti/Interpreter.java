package com.meti;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public record Interpreter(String input) {

    private static Option getString11(String input, Map<String, String> declarations) {
        if (input.startsWith("let ")) {
            var name = input.substring("let ".length(), input.indexOf('=')).strip();
            var value = input.substring(input.indexOf('=') + 1).strip();
            declarations.put(name, value);
            return new None();
        } else if (declarations.containsKey(input)) {
            return new Some(declarations.get(input));
        } else {
            return new Some(interpretValue(input));
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
            var result = getString11(line, declarations);
            if (i == lines.size() - 1) {
                if (result.isPresent()) {
                    return result.unwrapOrPanic();
                }
            }
        }

        return "";
    }
}