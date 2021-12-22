package com.meti;

import java.util.ArrayList;

public record MCCompiler(String input) {
    private static String compileMultiple(String input) {
        var lines = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ';' && depth == 0) {
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

    private static String compileNode(String input) {
        if (input.startsWith("return ")) {
            var value = input.substring("return ".length()).trim();
            var compiled = compileNode(value);
            return "return " + compiled + ";";
        } else if (input.startsWith("{") && input.endsWith("}")) {
            var value = input.substring(1, input.length() - 1);
            return "{" + compileMultiple(value) + "}";
        } else if (input.startsWith("const ")) {
            var separator = input.indexOf(':');
            var name = input.substring("const ".length(), separator).trim();
            var valueSeparator = input.indexOf('=');
            var typeString = input.substring(separator + 1, valueSeparator).trim();
            var type = compileType(typeString);
            var value = input.substring(valueSeparator + 1).trim();
            return type + " " + name + "=" + value + ";";
        } else if (input.startsWith("def ")) {
            var paramStart = input.indexOf('(');
            var name = input.substring("def ".length(), paramStart).trim();

            var typeSeparator = input.indexOf(':');
            var returnSeparator = input.indexOf("=>");
            var typeString = input.substring(typeSeparator + 1, returnSeparator).trim();
            var type = MCCompiler.compileType(typeString);

            var bodyString = input.substring(returnSeparator + "=>".length()).trim();
            var bodyRendered = compileNode(bodyString);

            return type + " " + name + "()" + bodyRendered;
        } else {
            try {
                Integer.parseInt(input);
                return input;
            } catch (NumberFormatException e) {
                return "";
            }
        }
    }

    private static String compileType(String typeString) {
        return switch (typeString) {
            case "I16" -> "int";
            case "Void" -> "void";
            default -> "unsigned int";
        };
    }

    String compile() {
        return compileMultiple(input);
    }
}
