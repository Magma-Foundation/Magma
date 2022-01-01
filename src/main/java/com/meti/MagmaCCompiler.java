package com.meti;

import java.util.ArrayList;

public record MagmaCCompiler(String input) {
    String compile() throws CompileException {
        var lines = split(input);
        var output = new StringBuilder();
        for (String line : lines) {
            output.append(lexNode(line));
        }
        return output.toString();
    }

    private String compileFunction(String input) throws LexException {
        var paramStart = input.indexOf('(');

        var depth = 0;
        var paramEnd = -1;
        for (int i = paramStart + 1; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ')' && depth == 0) {
                paramEnd = i;
                break;
            } else {
                if (c == '(') depth++;
                if (c == ')') depth--;
            }
        }

        var name = new Input(input).slice("def ".length(), paramStart);
        var typeSeparator = input.indexOf(':', paramEnd);
        var valueSeparator = input.lastIndexOf("=>");
        var inputType = new Input(input).slice(typeSeparator + 1, valueSeparator);
        var outputType = lexField(new Input(inputType), name);

        var parameterString = new Input(input).slice(paramStart + 1, paramEnd);
        var parameters = parameterString.split(",");
        var output = new ArrayList<String>();
        for (String parameter : parameters) {
            if (!parameter.isBlank()) {
                var separator = parameter.indexOf(':');
                var paramName = new Input(parameter).slice(0, separator);
                var paramType = new Input(parameter).slice(separator + 1, parameter.length());
                output.add(lexField(new Input(paramType), paramName));
            }
        }

        var paramsOutput = String.join(",", output);
        var value = new Input(input).slice(valueSeparator + "=>".length(), input.length());
        var compiledValue = lexNode(value);
        return outputType + "(" + paramsOutput + ")" + compiledValue;
    }

    private String lexNode(String input) throws LexException {
        if (input.startsWith("{") && input.endsWith("}")) {
            var linesString = input.substring(1, input.length() - 1);
            var lines = split(linesString);
            var output = new StringBuilder();
            for (String line : lines) {
                output.append(lexNode(line));
            }
            return "{" + output + "}";
        }

        if (input.startsWith("return ")) {
            var value = new Input(input).slice("return ".length(), input.length());
            var compiled = lexNode(value);
            return "return " + compiled + ";";
        }

        if (input.startsWith("def ")) {
            return compileFunction(input);
        }

        try {
            Integer.parseInt(input);
            return input;
        } catch (NumberFormatException e) {
            return input;
        }
    }

    private String lexField(Input input, String suffix) throws LexException {
        var separator = input.getInput().indexOf("=>");
        if (separator != -1) {
            var returnType = input.slice(separator + "=>".length(), input.getInput().length());
            return lexField(input, "(*" + suffix + "())");
        }
        return switch (input.getInput()) {
            case "I16" -> "int";
            case "U16" -> "unsigned int";
            case "Void" -> "void";
            default -> throw new LexException("Unknown type: " + input.getInput());
        } + " " + suffix;
    }

    private ArrayList<String> split(String input) {
        var lines = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (depth == 0 && c == ';') {
                lines.add(buffer.toString().trim());
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                buffer.append(c);
            }
        }
        lines.add(buffer.toString().trim());
        lines.removeIf(String::isEmpty);
        return lines;
    }

}
