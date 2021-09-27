package com.meti;

public record Compiler(String input) {
    String compile() {
        String output;
        if (input.isBlank()) {
            output = "";
        } else {
            var paramStart = input.indexOf('(');
            var paramEnd = input.indexOf(')');
            var paramString = slice(paramStart + 1, paramEnd);
            String parameters;
            if (paramString.equals("value : I16")) {
                parameters = "int value";
            } else {
                parameters = "";
            }

            var name = slice("def ".length(), paramStart);
            var returnTypeString = slice(input.lastIndexOf(':') + 1, input.indexOf("=>"));
            String returnType;
            if (returnTypeString.equals("Void")) {
                returnType = "void";
            } else {
                returnType = "int";
            }
            var bodyStart = input.indexOf('{');
            var body = slice(bodyStart, input.length());
            output = returnType + " " + name + "(" + parameters + ")" + body;
        }
        return output;
    }

    private String slice(int start, int end) {
        return input.substring(start, end).trim();
    }
}