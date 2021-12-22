package com.meti;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class MCCompiler {
    private final String input;
    private final Set<String> names = new HashSet<>();

    public MCCompiler(String input) {
        this.input = input;
    }

    private static String compileType(String typeString) {
        return switch (typeString) {
            case "I16" -> "int";
            case "Void" -> "void";
            default -> "unsigned int";
        };
    }

    private static String resolve(String value) {
        try {
            Integer.parseInt(value);
            return "int";
        } catch (NumberFormatException e) {
            return "";
        }
    }

    String compile() throws CompileException {
        return compileMultiple(input);
    }

    private String compileMultiple(String input) throws CompileException {
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

    private String compileNode(String input) throws CompileException {
        if (input.startsWith("return ")) {
            var value = input.substring("return ".length()).trim();
            var compiled = compileNode(value);
            return "return " + compiled + ";";
        } else if (input.startsWith("{") && input.endsWith("}")) {
            var value = input.substring(1, input.length() - 1);
            return "{" + compileMultiple(value) + "}";
        } else if (input.startsWith("const ") || input.startsWith("let ")) {
            var typeSeparator = input.indexOf(':');
            var valueSeparator = input.indexOf('=');

            var nameEnd = typeSeparator != -1 ? typeSeparator : valueSeparator;
            int nameStart;
            if (input.startsWith("const ")) {
                nameStart = "const ".length();
            } else {
                nameStart = "let ".length();
            }

            var name = input.substring(nameStart, nameEnd).trim();

            if (!names.contains(name)) {
                names.add(name);
            } else {
                throw new CompileException(name + " is already defined.");
            }

            var value = input.substring(valueSeparator + 1).trim();

            String type;
            if (typeSeparator != -1) {
                var typeString = input.substring(typeSeparator + 1, valueSeparator).trim();
                type = compileType(typeString);
            } else {
                type = resolve(value);
            }

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
}
