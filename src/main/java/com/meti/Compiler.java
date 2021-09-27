package com.meti;

public record Compiler(String input) {
    String compile() {
        String output;
        if (input.isBlank()) {
            output = "";
        } else {
            var paramStart = input.indexOf('(');
            var paramEnd = input.indexOf(')');
            var paramString = slice(input, paramStart + 1, paramEnd);
            String parameters;
            if (paramString.endsWith(" : I16")) {
                var separator = paramString.indexOf(':');
                var name = slice(paramString, 0, separator);
                parameters = "int " + name;
            } else {
                parameters = "";
            }

            var name = slice(input, "def ".length(), paramStart);
            var returnTypeString = slice(input, input.lastIndexOf(':') + 1, input.indexOf("=>"));
            String returnType;
            if (returnTypeString.equals("Void")) {
                returnType = "void";
            } else {
                returnType = "int";
            }
            var bodyStart = input.indexOf('{');
            var body = slice(input, bodyStart, input.length());
            output = returnType + " " + name + "(" + parameters + ")" + body;
        }
        return output;
    }

    private String slice(String value, int start, int end) {
        return value.substring(start, end).trim();
    }
}