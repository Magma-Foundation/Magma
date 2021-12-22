package com.meti;

import java.util.ArrayList;

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

    private String compileMultiple(String input) throws CompileException {
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

        var output = new StringBuilder();
        for (String line : lines) {
            output.append(compileNode(line));
        }
        return output.toString();
    }

    private String compileNativeImport(String input) {
        var nameString = input.substring("native import ".length());
        String name;
        if (nameString.startsWith("\"") && nameString.endsWith("\"")) {
            name = nameString.substring(1, nameString.length() - 1);
        } else {
            name = nameString;
        }
        return "#include <" + name + ".h>\n";
    }

    private String compileNode(String input) throws CompileException {
        if (input.startsWith("native import ")) {
            return compileNativeImport(input);
        } else if (input.startsWith("'") && input.endsWith("'")) {
            return input;
        } else if (input.startsWith("{") && input.endsWith("}")) {
            var value = input.substring(1, input.length() - 1);
            return "{" + compileMultiple(value) + "}";
        } else if (input.startsWith("def ") || input.startsWith("native def ")) {
            var paramStart = input.indexOf('(');
            var nameStart = input.startsWith("def")
                    ? "def ".length()
                    : "native def ".length();

            var name = input.substring(nameStart, paramStart).trim();

            var typeSeparator = input.indexOf(':');
            var returnSeparator = input.indexOf("=>");
            int typeEnd = 0;
            if (returnSeparator != -1) {
                typeEnd = returnSeparator;
            } else {
                typeEnd = input.length();
            }
            var typeString = input.substring(typeSeparator + 1, typeEnd).trim();
            var type = MCCompiler.compileType(typeString);

            if (returnSeparator != -1) {
                var bodyString = input.substring(returnSeparator + "=>".length()).trim();
                var bodyRendered = compileNode(bodyString);

                return type + " " + name + "()" + bodyRendered;
            } else {
                return "";
            }
        } else if (input.startsWith("const ") || input.startsWith("let ")) {
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
        } else if (input.contains("=")) {
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
        } else if (input.startsWith("return ")) {
            var value = input.substring("return ".length()).trim();
            var compiled = compileNode(value);
            return "return " + compiled + ";";
        } else {
            try {
                Integer.parseInt(input);
                return input;
            } catch (NumberFormatException e) {
                throw new CompileException("Unknown token: " + input);
            }
        }
    }

}
