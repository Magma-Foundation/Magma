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
            var name = slice(input, "def ".length(), paramStart);
            var typeSeparator = input.indexOf(':');
            var valueSeparator = input.lastIndexOf("=>");
            var inputType = slice(input, typeSeparator + 1, valueSeparator);
            var outputType = lexTypeString(inputType, name);
            var value = slice(input, valueSeparator + "=>".length(), input.length());
            return outputType + "()" + value;
        }
        return "";
    }

    private String lexTypeString(String input, String suffix) {
        var separator = input.indexOf("=>");
        if (separator != -1) {
            var returnType = slice(input, separator + "=>".length(), input.length());
            return lexTypeString(returnType, "(*" + suffix + "())");
        }
        return switch (input) {
            case "I16" -> "int";
            case "U16" -> "unsigned int";
            default -> "void";
        } + " " + suffix;
    }

    private String slice(String input, int start, int end) {
        return input.substring(start, end).trim();
    }
}
