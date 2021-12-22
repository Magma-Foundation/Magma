package com.meti;

public record MCCompiler(String input) {
    String compile() {
        var lines = input.split(";");
        var output = new StringBuilder();
        for (String line : lines) {
            output.append(compileNode(line));
        }
        return output.toString();
    }

    private static String compileNode(String input) {
        if (input.startsWith("const ")) {
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
            var bodyRendered = bodyString.equals("{return 0;}")
                    ? "{return 0;}"
                    : "{}";

            return type + " " + name + "()" + bodyRendered;
        }
        return "";
    }

    private static String compileType(String typeString) {
        return switch (typeString) {
            case "I16" -> "int";
            case "Void" -> "void";
            default -> "unsigned int";
        };
    }
}
