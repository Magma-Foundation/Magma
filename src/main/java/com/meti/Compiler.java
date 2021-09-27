package com.meti;

public record Compiler(String input) {
    String compile() {
        return compileLine(this.input);
    }

    private String compileLine(String lineString) {
        var lineInput = new Input(lineString);

        String output;
        if (lineString.isBlank()) {
            output = "";
        } else if (lineString.startsWith("{") && lineString.endsWith("}")) {
            var body = lineInput.slice(lineInput.firstIndexOfChar('{') + 1, lineInput.length() - 1);
            var lines = body.split(";");
            var builder = new StringBuilder();
            for (String line : lines) {
                builder.append(compileLine(line));
            }
            output = "{" + builder + "}";
        } else if (lineString.startsWith("const ")) {
            output = parseField(lineInput) + ";";
        } else {
            var paramStart = lineString.indexOf('(');
            var paramEnd = lineString.indexOf(')');
            var paramSlice = lineInput.slice(paramStart + 1, paramEnd);
            var parameters = parseParameters(paramSlice);

            var name = lineInput.slice("def ".length(), paramStart);
            var returnTypeString = lineInput.slice(lineString.lastIndexOf(':') + 1, lineString.indexOf("=>"));
            var returnType = resolveTypeName(returnTypeString);
            var bodyStart = lineString.indexOf('{');
            var body = lineInput.slice(bodyStart, lineString.length());
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
            var valueOutput = valueSeparator != -1 ? '=' + input.slice(valueSeparator + 1) : "";
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