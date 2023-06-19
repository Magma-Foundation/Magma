package com.meti;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public record Interpreter(String input) {

    private static String interpretStatement(String input, Map<String, String> declarations) {
        if (input.startsWith("let ")) {
            var name = input.substring("let ".length(), input.indexOf('=')).strip();
            var value = input.substring(input.indexOf('=') + 1).strip();
            declarations.put(name, value);
            return null;
        } else if (declarations.containsKey(input)) {
            return declarations.get(input);
        } else {
            return interpretValue(input);
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
            var result = interpretStatement(line, declarations);
            if (i == lines.size() - 1) {
                return result;
            }
        }

        return "";
    }
}