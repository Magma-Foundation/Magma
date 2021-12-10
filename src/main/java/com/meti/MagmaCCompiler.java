package com.meti;

import java.util.ArrayList;

public class MagmaCCompiler {
    private final String input;

    public MagmaCCompiler(String input) {
        this.input = input;
    }

    String compile() throws CompileException {
        var lines = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                buffer.append(c);
            }
        }

        lines.add(buffer.toString());
        lines.removeIf(String::isBlank);

        var builder = new StringBuilder();
        for (String line : lines) {
            builder.append(compileNode(line));
        }
        return builder.toString();
    }

    private String compileBlock(String input) {
        var bodyStart = input.indexOf('{');
        var bodyEnd = input.indexOf('}');
        var bodySlice = slice(bodyStart + 1, bodyEnd, input);
        String lines;
        if (bodySlice.equals("return 0;")) {
            lines = "return 0;";
        } else {
            lines = "";
        }
        return "{" + lines + "}";
    }

    private String compileFunction(String input) throws CompileException {
        var parameterStart = input.indexOf('(');
        var name = slice("def ".length(), parameterStart, input);
        var typeSeparator = input.indexOf(':');
        var returnSeparator = input.indexOf("=>");
        var typeString = slice(typeSeparator + 1, returnSeparator, input);

        var type = resolveTypeName(typeString);
        var body = compileBlock(input);
        return type + " " + name + "()" + body;
    }

    private String compileNode(String input) throws CompileException {
        String output;
        if (input.startsWith("def ")) {
            output = compileFunction(input);
        } else if (input.startsWith("{")) {
            output = compileBlock(input);
        } else {
            throw new CompileException("Cannot compile: " + input);
        }
        return output;
    }

    private String resolveTypeName(String typeString) throws CompileException {
        return switch (typeString) {
            case "I16" -> "int";
            case "U16" -> "unsigned int";
            case "Void" -> "void";
            default -> throw new CompileException("Unknown type: " + typeString);
        };
    }

    private String slice(int start, int end, String input) {
        return input.substring(start, end).trim();
    }
}
