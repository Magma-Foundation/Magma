package com.meti;

import java.util.ArrayList;

public record MagmaCCompiler(String input) {
    String compile() throws CompileException {
        var lines = split(input);
        var output = new StringBuilder();
        for (String line : lines) {
            output.append(lexNode(new Input(line)));
        }
        return output.toString();
    }

    private String compileFunction(Input input) throws LexException {
        var paramStart = input.firstIndexOfChar('(');

        var depth = 0;
        var paramEnd = -1;
        for (int i = paramStart + 1; i < input.length(); i++) {
            var c = input.apply(i);
            if (c == ')' && depth == 0) {
                paramEnd = i;
                break;
            } else {
                if (c == '(') depth++;
                if (c == ')') depth--;
            }
        }

        var name = input.slice("def ".length(), paramStart);
        var typeSeparator = input.firstIndexOfCharFrom(':', paramEnd);
        var valueSeparator = input.lastIndexOfString();
        var inputType = input.slice(typeSeparator + 1, valueSeparator);
        var outputType = lexField(inputType, name);

        var parameterString = input
                .slice(paramStart + 1, paramEnd)
                .compute();

        var parameters = parameterString.split(",");
        var output = new ArrayList<String>();
        for (String parameter : parameters) {
            if (!parameter.isBlank()) {
                var separator = parameter.indexOf(':');
                var input1 = new Input(parameter);
                var paramName = input1.slice(0, separator);
                var paramType = input1.slice(separator + 1, parameter.length());
                output.add(lexField(paramType, paramName));
            }
        }

        var paramsOutput = String.join(",", output);
        var value = input.sliceToEnd(valueSeparator + "=>".length());
        var compiledValue = lexNode(value);
        return outputType + "(" + paramsOutput + ")" + compiledValue;
    }

    private String lexBlock(Input input) throws LexException {
        var linesString = input.slice(1, input.length() - 1);
        var lines = split(linesString.compute());
        var output = new StringBuilder();
        for (String line : lines) {
            output.append(lexNode(new Input(line)));
        }
        return "{" + output + "}";
    }

    private String lexField(Input input, Input suffix) throws LexException {
        var separator = input.firstIndexOfSlice();
        if (separator != -1) {
            var paramStart = input.firstIndexOfChar('(');
            var paramEnd = input.firstIndexOfChar(')');
            var paramType = input.slice(paramStart + 1, paramEnd);
            String parameter;
            if (paramType.isEmpty()) {
                parameter = "";
            } else {
                parameter = lexField(paramType, new Input(""));
            }

            var slice = input.sliceToEnd(separator + "=>".length());
            return lexField(slice, new Input("(*" + suffix.compute() + "(" + parameter.trim() + "))"));
        }
        return switch (input.compute()) {
            case "I16" -> "int";
            case "U16" -> "unsigned int";
            case "Void" -> "void";
            default -> throw new LexException("Unknown type: " + input.compute());
        } + " " + suffix.compute();
    }

    private String lexNode(Input input) throws LexException {
        if (input.startsWith("{") && input.endsWith()) {
            return lexBlock(input);
        }

        if (input.startsWith("return ")) {
            return lexReturn(input);
        }

        if (input.startsWith("def ")) {
            return compileFunction(input);
        }

        try {
            Integer.parseInt(input.compute());
            return input.compute();
        } catch (NumberFormatException e) {
            return input.compute();
        }
    }

    private String lexReturn(Input input) throws LexException {
        var value = input.sliceToEnd("return ".length());
        var compiled = lexNode(value);
        return "return " + compiled + ";";
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
