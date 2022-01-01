package com.meti;

import java.util.ArrayList;

public record MagmaCCompiler(String input) {
    String compile() {
        var lines = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (depth == 0 && c == ';') {
                lines.add(buffer.toString().trim());
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                buffer.append(c);
            }
        }
        lines.add(buffer.toString().trim());
        lines.removeIf(String::isEmpty);

        var output = new StringBuilder();
        for (String line : lines) {
            output.append(compileNode(line));
        }
        return output.toString();
    }

    private String compileFunction(String input) {
        var paramStart = input.indexOf('(');

        var depth = 0;
        var paramEnd = -1;
        for (int i = paramStart + 1; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ')' && depth == 0) {
                paramEnd = i;
                break;
            } else {
                if (c == '(') depth++;
                if (c == ')') depth--;
            }
        }

        var name = slice(input, "def ".length(), paramStart);
        var typeSeparator = input.indexOf(':', paramEnd);
        var valueSeparator = input.lastIndexOf("=>");
        var inputType = slice(input, typeSeparator + 1, valueSeparator);
        var outputType = lexTypeString(inputType, name);

        var parameterString = slice(input, paramStart + 1, paramEnd);
        var parameters = parameterString.split(",");
        var output = new ArrayList<String>();
        for (String parameter : parameters) {
            if (!parameter.isBlank()) {
                var separator = parameter.indexOf(':');
                var paramName = slice(parameter, 0, separator);
                var paramType = slice(parameter, separator + 1, parameter.length());
                output.add(lexTypeString(paramType, paramName));
            }
        }

        var paramsOutput = String.join(",", output);
        var value = slice(input, valueSeparator + "=>".length(), input.length());
        return outputType + "(" + paramsOutput + ")" + value;
    }

    private String compileNode(String input) {
        if (input.startsWith("return ")) {
            var value = slice(input, "return ".length(), input.length());
            var compiled = compileNode(value);
            return "return " + compiled + ";";
        } else if (input.startsWith("def ")) {
            return compileFunction(input);
        }

        try {
            Integer.parseInt(input);
            return input;
        } catch (NumberFormatException e) {
            return "";
        }
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
