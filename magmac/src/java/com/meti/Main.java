package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        var source = Paths.get(".", "magmac", "src", "java", "com", "meti", "Main.java");
        try {
            var input = Files.readString(source);
            var target = source.resolveSibling("Main.mgs");
            Files.writeString(target, compile(input));
        } catch (IOException | CompileException e) {
            throw new RuntimeException(source.toAbsolutePath().toString(), e);
        }
    }

    private static String compile(String input) throws CompileException {
        var lines = split(input);

        var output = new StringBuilder();
        for (String line : lines) {
            output.append(compileRootMember(line));
        }

        return output.toString();
    }

    private static ArrayList<String> split(String input) {
        var lines = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;
        var inQuotes = false;

        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == '\'') {
                inQuotes = !inQuotes;
            }

            if (inQuotes && c == '\\') {
                buffer.append(c); // Append the backslash
                i++; // Move to the next character
                if (i < input.length()) { // Check if it is not the end of the string
                    c = input.charAt(i);
                    buffer.append(c); // Append the escaped character
                    continue; // Skip further checks and continue
                }
            }

            if (!inQuotes) {
                if (c == ';' && depth == 0) {
                    lines.add(buffer.toString());
                    buffer = new StringBuilder();
                } else if (c == '}' && depth == 1) {
                    buffer.append(c);
                    depth = 0;
                    lines.add(buffer.toString());
                    buffer = new StringBuilder();
                } else {
                    if (c == '{' || c == '(') depth++;
                    if (c == '}' || c == ')') depth--;
                    buffer.append(c);
                }
            } else {
                buffer.append(c);
            }
        }
        lines.add(buffer.toString());
        return lines;
    }

    private static String compileRootMember(String input) throws CompileException {
        if (input.isBlank() || input.startsWith("package ")) {
            return "";
        }

        var stripped = input.strip();
        if (stripped.startsWith("import ")) {
            var segments = stripped.substring("import ".length());
            var separator = segments.lastIndexOf('.');
            var parent = segments.substring(0, separator);
            var child = segments.substring(separator + 1);
            return "import { " + child + " } from " + parent + ";\n";
        }

        var classIndex = stripped.indexOf("class ");
        if (classIndex != -1) {
            var contentStart = stripped.indexOf('{');
            var name = stripped.substring(classIndex + "class ".length(), contentStart);
            var modifierString = stripped.startsWith("public ") ? "export " : "";

            var content = stripped.substring(contentStart + 1, stripped.lastIndexOf('}'));
            var splitContent = split(content);
            var output = new StringBuilder();
            for (String s : splitContent) {
                if (!s.isBlank()) {
                    output.append(compileClassMember(s));
                }
            }

            return modifierString + "class def " + name + "() => {\n" + output + "}";
        }

        throw createUnknownInputError(input, "root member");
    }

    private static CompileException createUnknownInputError(String input, String type) {
        return new CompileException("Unknown " + type + ": " + input);
    }

    private static String compileClassMember(String input) throws CompileException {
        try {
            return compileMethod(input)
                    .orElseThrow(() -> createUnknownInputError(input, "input"));
        } catch (CompileException e) {
            throw createFail(input, e);
        }
    }

    private static CompileException createFail(String input, CompileException e) {
        return new CompileException("Failed to compile: " + input, e);
    }

    private static Optional<String> compileMethod(String input) throws CompileException {
        var paramStart = input.indexOf('(');
        if (paramStart == -1) return Optional.empty();

        var keys = input.substring(0, paramStart).strip();
        var separator = keys.lastIndexOf(' ');
        if (separator == -1) return Optional.empty();

        var name = keys.substring(separator + 1);
        var contentStart = input.indexOf('{');
        var contentEnd = input.lastIndexOf('}');
        var inputContent = split(input.substring(contentStart + 1, contentEnd));
        var outputContent = new StringBuilder();
        for (String s : inputContent) {
            if (!s.isBlank()) {
                outputContent.append(compileStatement(s));
            }
        }

        return Optional.of("\tdef " + name + "() => {\n" + outputContent + "\t}\n");
    }

    private static String compileStatement(String input) throws CompileException {
        try {
            return compileFor(input)
                    .or(() -> compileCatch(input))
                    .or(() -> compileReturn(input))
                    .or(() -> compileDeclaration(input))
                    .or(() -> compileInvocation(input))
                    .orElseThrow(() -> createUnknownInputError(input, "statement"));
        } catch (CompileException e) {
            throw createFail(input, e);
        }
    }

    private static Optional<String> compileInvocation(String input) {
        var start = input.indexOf('(');
        if (start == -1) return Optional.empty();

        var end = input.lastIndexOf(')');
        if (end == -1) return Optional.empty();

        var caller = input.substring(0, start);
        var argument = input.substring(start + 1, end);

        return Optional.of(caller + "(" + argument + ");\n");
    }

    private static Optional<String> compileReturn(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("return ")) {
            return Optional.of("\t\treturn 0;\n");
        } else {
            return Optional.empty();
        }
    }

    private static Optional<String> compileFor(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("for ")) {
            return Optional.of("\t\tfor (){}\n");
        }

        return Optional.empty();
    }

    private static Optional<String> compileCatch(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("catch ")) {
            return Optional.of("\t\tcatch () {}\n");
        } else {
            return Optional.empty();
        }
    }

    private static Optional<String> compileDeclaration(String input) {
        var separator = input.indexOf('=');
        if (separator == -1) return Optional.empty();

        var slice = input.substring(0, separator).strip();
        var nameSeparator = slice.lastIndexOf(' ');
        var name = slice.substring(nameSeparator + 1).strip();
        if (name.isEmpty()) return Optional.empty();

        var first = name.charAt(0);
        if (!Character.isLetter(first)) return Optional.empty();

        for (int i = 1; i < name.length(); i++) {
            var c = name.charAt(i);
            if (!Character.isLetter(c) && !Character.isDigit(c)) {
                return Optional.empty();
            }
        }

        return Optional.of("\t\tlet " + name + " = 0;\n");
    }

    static class CompileException extends Exception {
        public CompileException(String message) {
            super(message);
        }

        public CompileException(String message, Exception cause) {
            super(message, cause);
        }
    }
}
