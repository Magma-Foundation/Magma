package com.meti;

public record Compiler(String input) {
    private static String lexNode(String input) {
        if (input.startsWith("def ")) {
            var paramStart = input.indexOf('(');
            var name = slice(input, "def ".length(), paramStart);
            var typeSeparator = input.indexOf(':');
            var valueSeparator = input.indexOf("=>");
            var typeString = slice(input, typeSeparator + 1, valueSeparator);
            String type;
            if (typeString.equals("I16")) {
                type = "int";
            } else {
                type = "unsigned int";
            }
            return type + " " + name + "(){return 0;}";
        }
        if (input.startsWith("return ")) {
            var value = slice(input, "return ".length(), input.length());
            return "return " + lexNode(value) + ";";
        }
        try {
            Integer.parseInt(input);
            return input;
        } catch (NumberFormatException e) {
            return "";
        }
    }

    private static String slice(String input, int start, int end) {
        return input.substring(start, end).trim();
    }

    String compile() {
        if (input.isBlank()) return input;
        return lexNode(input);
    }
}
