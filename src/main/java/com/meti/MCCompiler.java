package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

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
        var name = slice(input, 0, separator);
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

        var nameString = slice(input, nameStart, nameEnd);
        var valueString = input.substring(valueSeparator + 1).trim();

        String type;
        if (typeSeparator == -1) {
            type = resolve(valueString);
        } else {
            var typeString = slice(input, typeSeparator + 1, valueSeparator);
            type = compileType(typeString);
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

        var name = slice(input, nameStart, paramStart);

        var typeSeparator = input.indexOf(':');
        var returnSeparator = input.indexOf("=>");
        var typeEnd = returnSeparator == -1 ? input.length() : returnSeparator;
        var typeString = slice(input, typeSeparator + 1, typeEnd);
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
        }
        if (input.startsWith("'") && input.endsWith("'")) {
            return input;
        }
        if (input.startsWith("{") && input.endsWith("}")) {
            return compileBlock(input);
        }
        if (input.startsWith("def ") || input.startsWith("native def ")) {
            return compileFunction(input);
        }
        if (input.startsWith("const ") || input.startsWith("let ")) {
            return compileDeclaration(input);
        }
        if (input.contains("=")) {
            return compileAssignment(input);
        }
        lex(new ReturnLexer(input));
        return new IntegerLexer(input)
                .lex().orElseThrow(() -> new CompileException("Unknown token: " + input));
    }

    private Option<String> lex(ReturnLexer returnLexer) throws CompileException {
        if (returnLexer.getInput().startsWith("return ")) {
            var value = slice(returnLexer.getInput(), "return ".length(), returnLexer.getInput().length());
            var compiled = compileNode(value);
            return new Some<>("return " + compiled + ";");
        }
        return new None<>();
    }

    private String slice(String input, int length, int length2) {
        return input.substring(length, length2).trim();
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
