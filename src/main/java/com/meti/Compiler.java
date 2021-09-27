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
            var parameters = parseParameters(paramString);

            var name = slice(input, "def ".length(), paramStart);
            var returnTypeString = slice(input, input.lastIndexOf(':') + 1, input.indexOf("=>"));
            var returnType = resolveTypeName(returnTypeString);
            var bodyStart = input.indexOf('{');
            var body = slice(input, bodyStart, input.length());
            output = returnType + " " + name + "(" + parameters + ")" + body;
        }
        return output;
    }

    private String parseParameters(String paramString) {
        String parameters;
        if (paramString.endsWith(" : I16")) {
            var separator = paramString.indexOf(':');
            var name = slice(paramString, 0, separator);
            parameters = "int " + name;
        } else {
            parameters = "";
        }
        return parameters;
    }

    private String resolveTypeName(String returnTypeString) {
        String returnType;
        if (returnTypeString.equals("Void")) {
            returnType = "void";
        } else {
            returnType = "int";
        }
        return returnType;
    }

    private String slice(String value, int start, int end) {
        return value.substring(start, end).trim();
    }
}