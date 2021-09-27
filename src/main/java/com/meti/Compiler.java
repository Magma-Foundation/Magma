package com.meti;

public record Compiler(Input input) {
    String compile() {
        return compileLine(input.value());
    }

    private String compileLine(String lineString) {
        var input = new Input(lineString);

        String output;
        if (lineString.isBlank()) {
            output = "";
        } else if (lineString.startsWith("{") && lineString.endsWith("}")) {
            output = parseBody(input);
        } else if (lineString.startsWith("const ")) {
            output = parseField(input) + ";";
        } else {
            output = parseFunction(input);
        }
        return output;
    }

    private String parseBody(Input input) {
        var body = input.slice(input.firstIndexOfChar('{') + 1, input.length() - 1);
        var lines = body.split(";");
        var builder = new StringBuilder();
        for (String line : lines) {
            builder.append(compileLine(line));
        }
        return "{" + builder + "}";
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
            var valueOutput = valueSeparator != -1 ? '=' + input.slice(valueSeparator + 1) : "";
            parameters = type + " " + name + valueOutput;
        }
        return parameters;
    }

    private String parseFunction(Input input) {
        var paramStart = input.firstIndexOfChar('(');
        var paramEnd = input.firstIndexOfChar(')');
        var paramSlice = input.slice(paramStart + 1, paramEnd);
        var parameters = parseParameters(paramSlice);

        var name = input.slice("def ".length(), paramStart);

        var returnTypeString = input.slice(input.lastIndexOfChar() + 1, input.firstIndexOfSlice());
        var returnType = resolveTypeName(returnTypeString);

        var bodyStart = input.firstIndexOfChar('{');
        var body = input.slice(bodyStart, input.length());

        return returnType + " " + name + "(" + parameters + ")" + body;
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