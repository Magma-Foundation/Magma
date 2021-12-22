package com.meti;

public record MCCompiler(String input) {
    String compile() {
        if (input.startsWith("def ")) {
            var paramStart = input.indexOf('(');
            var name = input.substring("def ".length(), paramStart).trim();

            var typeSeparator = input.indexOf(':');
            var returnSeparator = input.indexOf("=>");
            var typeString = input.substring(typeSeparator + 1, returnSeparator).trim();
            var type = typeString.equals("I16") ? "int" : "unsigned int";

            return type + " " + name + "(){return 0;}";
        }
        return "";
    }
}
