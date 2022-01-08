package com.meti;

public record Compiler(String input) {
    String compile() {
        if (input.isBlank()) return input;
        if (input.startsWith("def ")) {
            var paramStart = input.indexOf('(');
            var name = input.substring("def ".length(), paramStart);
            var typeSeparator = input.indexOf(':');
            var valueSeparator = input.indexOf("=>");
            var typeString = input.substring(typeSeparator + 1, valueSeparator).trim();
            String type;
            if (typeString.equals("I16")) {
                type = "int";
            } else {
                type = "unsigned int";
            }
            return type + " " + name + "(){return 0;}";
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
