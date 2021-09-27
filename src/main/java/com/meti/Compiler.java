package com.meti;

public record Compiler(String input) {
    String compile() {
        var input1 = new Input(this.input);

        String output;
        if (input.isBlank()) {
            output = "";
        } else if (input.startsWith("const ")) {
            return parseField(input1) + ";";
        } else {
            var paramStart = input.indexOf('(');
            var paramEnd = input.indexOf(')');
            var paramSlice = input1.slice(paramStart + 1, paramEnd);
            var parameters = parseParameters(paramSlice);

            var name = input1.slice("def ".length(), paramStart);
            var returnTypeString = input1.slice(this.input.lastIndexOf(':') + 1, this.input.indexOf("=>"));
            var returnType = resolveTypeName(returnTypeString);
            var bodyStart = this.input.indexOf('{');
            var body = input1.slice(bodyStart, this.input.length());
            output = returnType + " " + name + "(" + parameters + ")" + body;
        }
        return output;
    }

    private String parseField(Input input) {
        String parameters;
        var typeSeparator = input.firstIndexOfChar(':');
        if (typeSeparator == -1) {
            parameters = "";
        } else {
            var valueSeparator = input.firstIndexOfChar('=');

            var keys = input.slice(0, typeSeparator);
            var name = keys.substring(keys.lastIndexOf(' ') + 1);
            var typeName = input.slice(typeSeparator + 1, valueSeparator == -1
                    ? input.length()
                    : valueSeparator);
            var type = resolveTypeName(typeName);
            var valueOutput = valueSeparator != -1 ? '=' + input.slice(valueSeparator + 1, input.length() - 1) : "";
            parameters = type + " " + name + valueOutput;
        }
        return parameters;
    }

    private StringBuilder parseParameters(String paramSlice) {
        var paramStrings = paramSlice.split(",");
        var parameters = new StringBuilder();
        if (paramStrings.length != 0) {
            parameters.append(parseField(new Input(paramStrings[0])));

            for (int i = 1; i < paramStrings.length; i++) {
                var paramString = paramStrings[i];
                var parsedParameter = parseField(new Input(paramString));
                parameters.append(",").append(parsedParameter);
            }
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