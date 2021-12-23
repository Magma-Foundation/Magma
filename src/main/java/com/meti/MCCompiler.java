package com.meti;

import java.util.ArrayList;
import java.util.List;

public final class MCCompiler {
    private final String input;
    private final Scope scope;

    public MCCompiler(String input) {
        this.input = input;
        this.scope = new Scope();
    }

    private static String compileType(String type) throws CompileException {
        return switch (type) {
            case "I8" -> "char";
            case "I16" -> "int";
            case "U16" -> "unsigned int";
            case "Void" -> "void";
            default -> throw new CompileException("Unknown type: " + type);
        };
    }

    private static String resolve(String value) {
        if (value.startsWith("'") && value.endsWith("'")) {
            return "char";
        }

        try {
            Integer.parseInt(value);
            return "int";
        } catch (NumberFormatException e) {
            return "";
        }
    }

    String compile() throws CompileException {
        return compileMultiple(input);
    }

    private String compileAssignment(String input) throws CompileException {
        var separator = input.indexOf('=');
        var name = input.substring(0, separator).trim();
        var valueString = input.substring(separator + 1).trim();
        var value = compileNode(valueString);
        var valueType = resolve(valueString);

        var definition = scope.apply(name).orElseThrow(() -> new CompileException(name + " is undefined."));
        if (!definition.isTyped(valueType)) {
            throw new CompileException("Assignment of type " + valueType + " is not the same as defined type as " + definition.type());
        }

        return name + "=" + value + ";";
    }

    private String compileBlock(String input) throws CompileException {
        var value = input.substring(1, input.length() - 1);
        return "{" + compileMultiple(value) + "}";
    }

    private String compileDeclaration(String input) throws CompileException {
        var typeSeparator = input.indexOf(':');
        var valueSeparator = input.indexOf('=');

        var nameEnd = typeSeparator != -1 ? typeSeparator : valueSeparator;
        int nameStart;
        if (input.startsWith("const ")) {
            nameStart = "const ".length();
        } else {
            nameStart = "let ".length();
        }

        var nameString = input.substring(nameStart, nameEnd).trim();
        var valueString = input.substring(valueSeparator + 1).trim();

        String type;
        if (typeSeparator != -1) {
            var typeString = input.substring(typeSeparator + 1, valueSeparator).trim();
            type = compileType(typeString);
        } else {
            type = resolve(valueString);
        }

        if (scope.isUndefined(nameString)) {
            scope.define(new Field(nameString, type));
        } else {
            throw new CompileException(nameString + " is already defined.");
        }

        var value = compileNode(valueString);
        return type + " " + nameString + "=" + value + ";";
    }

    private String compileFunction(String input) throws CompileException {
        var paramStart = input.indexOf('(');
        var nameStart = input.startsWith("def")
                ? "def ".length()
                : "native def ".length();

        var name = input.substring(nameStart, paramStart).trim();

        var typeSeparator = input.indexOf(':');
        var returnSeparator = input.indexOf("=>");
        var typeEnd = returnSeparator == -1 ? input.length() : returnSeparator;
        var typeString = input.substring(typeSeparator + 1, typeEnd).trim();
        var type = MCCompiler.compileType(typeString);

        if (returnSeparator == -1) {
            return "";
        } else {
            var bodyString = input.substring(returnSeparator + "=>".length()).trim();
            var bodyRendered = compileNode(bodyString);

            return type + " " + name + "()" + bodyRendered;
        }
    }

    private String compileMultiple(String input) throws CompileException {
        var lines = split(input);
        var output = new StringBuilder();
        for (String line : lines) {
            output.append(compileNode(line.trim()));
        }
        return output.toString();
    }

    private String compileNativeImport(String input) {
        var nameString = input.substring("native import ".length());
        var name = nameString.startsWith("\"") && nameString.endsWith("\"")
                ? nameString.substring(1, nameString.length() - 1) : nameString;
        return "#include <" + name + ".h>\n";
    }

    private String compileNode(String input) throws CompileException {
        if (input.startsWith("native import ")) {
            return compileNativeImport(input);
        } else if (input.startsWith("'") && input.endsWith("'")) {
            return input;
        } else if (input.startsWith("{") && input.endsWith("}")) {
            return compileBlock(input);
        } else if (input.startsWith("def ") || input.startsWith("native def ")) {
            return compileFunction(input);
        } else if (input.startsWith("const ") || input.startsWith("let ")) {
            return compileDeclaration(input);
        } else if (input.contains("=")) {
            return compileAssignment(input);
        } else if (input.startsWith("return ")) {
            return compileReturn(input);
        } else {
            try {
                Integer.parseInt(input);
                return input;
            } catch (NumberFormatException e) {
                throw new CompileException("Unknown token: " + input);
            }
        }
    }

    private String compileReturn(String input) throws CompileException {
        var value = input.substring("return ".length()).trim();
        var compiled = compileNode(value);
        return "return " + compiled + ";";
    }

    private List<String> split(String input) {
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
        return lines;
    }
}
