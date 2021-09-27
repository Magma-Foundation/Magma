package com.meti;

public record Compiler(String input) {
    String compile() {
        String output;
        if (input.isBlank()) {
            output = "";
        } else {
            var name = slice("def ".length(), input.indexOf('('));
            var returnTypeString = slice(input.indexOf(':') + 1, input.indexOf("=>"));
            String returnType;
            if (returnTypeString.equals("Void")) {
                returnType = "void";
            } else {
                returnType = "int";
            }
            var bodyStart = input.indexOf('{');
            var body = slice(bodyStart, input.length());
            output = returnType + " " + name + "()" + body;
        }
        return output;
    }

    private String slice(int start, int end) {
        return input.substring(start, end).trim();
    }
}