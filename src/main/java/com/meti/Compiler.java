package com.meti;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.meti.EmptyNode.EmptyNode_;

public record Compiler(Input input) {
    String compile() {
        var root = parseLine(input);
        var oldChildren = root.streamNodes().collect(Collectors.toList());
        var newChildren = new ArrayList<Node>();
        for (Node oldChild : oldChildren) {
            newChildren.add(parseLine(new Input(oldChild.getValue())));
        }
        var parsedRoot = root.withNodes(newChildren);
        return parsedRoot.render();
    }

    private Node parseLine(Input input) {
        if (input.isBlank()) {
            return EmptyNode_;
        } else if (input.startsWith("{") && input.endsWith("}")) {
            return parseBody(input);
        } else if (input.startsWith("const ")) {
            return new Declaration(parseField(input));
        } else {
            return parseFunction(input);
        }
    }

    private Node parseBody(Input input) {
        var start = input.firstIndexOfChar('{') + 1;
        var end = input.length() - 1;

        var body = input.slice(start, end);
        var lines = body.split(";");

        var children = new ArrayList<Node>();
        for (String line : lines) {
            children.add(new Content(line));
        }

        return new Block(children);
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

    private Node parseFunction(Input input) {
        var paramStart = input.firstIndexOfChar('(');
        var paramEnd = input.firstIndexOfChar(')');
        var paramSlice = input.slice(paramStart + 1, paramEnd);
        var parameters = parseParameters(paramSlice);

        var name = input.slice("def ".length(), paramStart);

        var returnTypeString = input.slice(input.lastIndexOfChar() + 1, input.firstIndexOfSlice());
        var returnType = resolveTypeName(returnTypeString);

        var bodyStart = input.firstIndexOfChar('{');
        var body = input.slice(bodyStart, input.length());

        return new Function(name, parameters, returnType, body);
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

    private ArrayList<String> parseParameters(String paramSlice) {
        var paramStrings = paramSlice.split(",");
        var parameters = new ArrayList<String>();
        for (String paramString : paramStrings) {
            if (!paramString.isBlank()) {
                parameters.add(parseField(new Input(paramString)));
            }
        }
        return parameters;
    }
}