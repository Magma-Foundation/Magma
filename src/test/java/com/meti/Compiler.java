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
            var inputType = input.substring(typeSeparator + 1, valueSeparator).trim();
            var outputType = inputType.equals("I16") ? "int" : "unsigned int";
            return outputType + " " + name + "(){return 0;}";
        }
        return "";
    }
}
