package com.meti;

import java.util.ArrayList;

public record Compiler(Input input) {
    String compile() {
        return compileLine(input).renderMagma();
    }

    private Node compileLine(Input input) {
        String output;
        if (input.isBlank()) {
            output = "";
        } else if (input.startsWith("{") && input.endsWith("}")) {
            output = parseBody(input);
        } else if (input.startsWith("const ")) {
            output = parseField(input) + ";";
        } else {
            output = parseFunction(input);
        }
        return new Node(output);
    }

    private String parseBody(Input input) {
        var body = input.slice(input.firstIndexOfChar('{') + 1, input.length() - 1);
        var lines = body.split(";");

        var children = new ArrayList<Node>();
        for (String line : lines) {
            children.add(compileLine(new Input(line)));
        }

        var builder = new StringBuilder();
        for (Node child : children) {
            builder.append(child.renderMagma());
        }

        return "{" + builder + "}";
    }

    private String parseField(Input input) {
        var typeSeparator = input.firstIndexOfChar(':');
        var valueSeparator = input.firstIndexOfChar('=');

        var keys = input.slice(0, typeSeparator);
        var name = keys.substring(keys.lastIndexOf(' ') + 1);
        var typeName = input.slice(typeSeparator + 1, valueSeparator == -1
                ? input.length()
                : valueSeparator);
        var type = resolveTypeName(typeName);
        var valueOutput = valueSeparator != -1 ? '=' + input.slice(valueSeparator + 1) : "";
        return type + " " + name + valueOutput;
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