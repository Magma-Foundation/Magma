package com.meti;

import java.util.ArrayList;

public record Compiler(String input) {
    String compile() {
        var lines = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (depth == 0 && c == ';') {
                lines.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                buffer.append(c);
            }
        }
        lines.add(buffer.toString());
        lines.removeIf(String::isBlank);

        var output = new StringBuilder();
        for (String line : lines) {
            output.append(compileNode(line));
        }
        return output.toString();
    }

    private String compileNode(String input) {
        if (input.startsWith("def ")) {
            var paramStart = input.indexOf('(');
            var name = input.substring("def ".length(), paramStart).trim();
            var typeSeparator = input.indexOf(':');
            var valueSeparator = input.indexOf("=>");
            var inputType = slice(input, typeSeparator, 1, valueSeparator);
            var outputType = switch (inputType) {
                case "I16" -> "int";
                case "U16" -> "unsigned int";
                default -> "void";
            };
            var value = slice(input, valueSeparator, "=>".length(), input.length());
            return outputType + " " + name + "()" + value;
        }
        return "";
    }

    private String slice(String input, int valueSeparator, int length, int length2) {
        return input.substring(valueSeparator + length, length2).trim();
    }
}
