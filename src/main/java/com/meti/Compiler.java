package com.meti;

public record Compiler(String input) {
    String compile() {
        String output;
        if (input.isBlank()) {
            output = "";
        } else {
            var paramStart = input.indexOf('(');
            var paramEnd = input.indexOf(')');
            var paramSlice = new Input(input).slice(paramStart + 1, paramEnd);
            var parameters = parseParameters(paramSlice);

            var name = new Input(input).slice("def ".length(), paramStart);
            var returnTypeString = new Input(input).slice(input.lastIndexOf(':') + 1, input.indexOf("=>"));
            var returnType = resolveTypeName(returnTypeString);
            var bodyStart = input.indexOf('{');
            var body = new Input(input).slice(bodyStart, input.length());
            output = returnType + " " + name + "(" + parameters + ")" + body;
        }
        return output;
    }

    private StringBuilder parseParameters(String paramSlice) {
        var paramStrings = paramSlice.split(",");
        var parameters = new StringBuilder();
        if (paramStrings.length != 0) {
            parameters.append(parseParameter(new Input(paramStrings[0])));

            for (int i = 1; i < paramStrings.length; i++) {
                var paramString = paramStrings[i];
                var parsedParameter = parseParameter(new Input(paramString));
                parameters.append(",").append(parsedParameter);
            }
        }
        return parameters;
    }

    private String parseParameter(Input input) {
        String parameters;
        var separator = input.firstIndexOfChar();
        if (separator == -1) {
            parameters = "";
        } else {
            var name = input.slice(0, separator);
            var typeName = input.slice(separator + 1);
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
}