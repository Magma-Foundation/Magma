package com.meti;

public record MCCompiler(String input) {
    String compile() {
        if (input.startsWith("def ")) {
            var paramStart = input.indexOf('(');
            var name = input.substring("def ".length(), paramStart).trim();

            var typeSeparator = input.indexOf(':');
            var returnSeparator = input.indexOf("=>");
            var typeString = input.substring(typeSeparator + 1, returnSeparator).trim();
            var type = switch (typeString) {
                case "I16" -> "int";
                case "Void" -> "void";
                default -> "unsigned int";
            };

            var bodyString = input.substring(returnSeparator + "=>".length()).trim();
            var bodyRendered = bodyString.equals("{return 0;}")
                    ? "{return 0;}"
                    : "{}";

            return type + " " + name + "()" + bodyRendered;
        }
        return "";
    }
}
