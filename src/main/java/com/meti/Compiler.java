package com.meti;

public record Compiler(String input) {
    String compile() {
        String output;
        if (input.isBlank()) {
            output = "";
        } else {
            var paramStart = input.indexOf('(');
            var paramEnd = input.indexOf(')');
            var paramSlice = slice(input, paramStart + 1, paramEnd);
            var parameters = parseParameters(paramSlice);

            var name = slice(input, "def ".length(), paramStart);
            var returnTypeString = slice(input, input.lastIndexOf(':') + 1, input.indexOf("=>"));
            var returnType = resolveTypeName(returnTypeString);
            var bodyStart = input.indexOf('{');
            var body = slice(input, bodyStart, input.length());
            output = returnType + " " + name + "(" + parameters + ")" + body;
        }
        return output;
    }

    private StringBuilder parseParameters(String paramSlice) {
        var paramStrings = paramSlice.split(",");
        var parameters = new StringBuilder();
        if (paramStrings.length != 0) {
            parameters.append(parseParameter(paramStrings[0]));

            for (int i = 1; i < paramStrings.length; i++) {
                var paramString = paramStrings[i];
                var parsedParameter = parseParameter(paramString);
                parameters.append(",").append(parsedParameter);
            }
        }
        return parameters;
    }

    private String parseParameter(String paramString) {
        String parameters;
        var separator = paramString.indexOf(':');
        if (separator == -1) {
            parameters = "";
        } else {
            var name = slice(paramString, 0, separator);
            var typeName = slice(paramString, separator + 1, paramString.length());
            var type = resolveTypeName(typeName);
            parameters = type + " " + name;
        }
        return parameters;
    }

    private String resolveTypeName(String returnTypeString) {
        String returnType;
        if (returnTypeString.equals("Void")) {
            returnType = "void";
        } else if (returnTypeString.equals("I16")) {
            returnType = "int";
        } else {
            returnType = "unsigned int";
        }
        return returnType;
    }

    private String slice(String value, int start, int end) {
        return value.substring(start, end).trim();
    }
}