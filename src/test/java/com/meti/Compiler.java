package com.meti;

public record Compiler(String input) {
    String compile() {
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
