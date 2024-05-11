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
        var depth = 0; // depth for brackets and braces
        var parenDepth = 0; // separate depth counter for parentheses
        var inQuotes = false;
        char quoteType = '\0'; // to track the type of quotes used

        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);

            // Handle quotes
            if ((c == '"' || c == '\'') && (i == 0 || input.charAt(i - 1) != '\\')) {
                if (!inQuotes) {
                    inQuotes = true;
                    quoteType = c;
                } else if (c == quoteType) {
                    inQuotes = false;
                }
            }

            if (inQuotes) {
                // Handle escape sequences within quotes
                if (c == '\\') {
                    buffer.append(c);
                    i++;
                    if (i < input.length()) {
                        c = input.charAt(i);
                        buffer.append(c);
                        continue;
                    }
                }
                buffer.append(c);
            } else {
                if (c == '(') {
                    parenDepth++;
                } else if (c == ')') {
                    parenDepth--;
                }

                if (parenDepth > 0) {
                    // Anything within parentheses is added directly
                    buffer.append(c);
                } else {
                    // Process other characters based on structural depth and parentheses
                    if (c == ';') {
                        if (depth == 0) {
                            lines.add(buffer.toString().trim());
                            buffer = new StringBuilder();
                            continue;
                        }
                    } else if (c == '{') {
                        depth++;
                    } else if (c == '}') {
                        depth--;
                        if (depth < 0) depth = 0; // Prevent depth from going negative
                    }

                    buffer.append(c);

                    if (c == '}' && depth == 0) {
                        lines.add(buffer.toString().trim());
                        buffer = new StringBuilder();
                    }
                }
            }
        }

        if (buffer.length() > 0) {
            lines.add(buffer.toString().trim());
        }
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
                var result = compileStatement(s).$();
                outputContent.append(result);
            }
        }

        return Optional.of("\tdef " + name + "() => {\n" + outputContent + "\t}\n");
    }

    private static Result<String, CompileException> compileStatement(String input) {
        try {
            return compileFor(input)
                    .or(() -> compileCatch(input))
                    .or(() -> compileReturn(input))
                    .or(() -> compileDeclaration(input))
                    .or(() -> compileInvocation(input))
                    .orElseThrow(() -> createUnknownInputError(input, "statement"));
        } catch (CompileException e) {
            return new Err<>(createFail(input, e));
        }
    }

    private static Optional<Result<String, CompileException>> compileInvocation(String input) {
        var start = input.indexOf('(');
        if (start == -1) return Optional.empty();

        var end = input.lastIndexOf(')');
        if (end == -1) return Optional.empty();

        var caller = input.substring(0, start);
        var argument = input.substring(start + 1, end);

        Result<String, CompileException> result;
        try {
            var rendered = compileValue(caller) + "(" + compileValue(argument) + ");\n";
            result = new Ok<>(rendered);
        } catch (CompileException e) {
            result = new Err<>(e);
        }
        return Optional.of(result);
    }

    private static String compileValue(String caller) throws CompileException {
        throw createUnknownInputError(caller, "value");
    }

    private static Optional<Result<String, CompileException>> compileReturn(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("return ")) {
            return Optional.of(new Ok<>("\t\treturn 0;\n"));
        } else {
            return Optional.empty();
        }
    }

    private static Optional<Result<String, CompileException>> compileFor(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("for ")) {
            return Optional.of(new Ok<>("\t\tfor (){}\n"));
        }

        return Optional.empty();
    }

    private static Optional<Result<String, CompileException>> compileCatch(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("catch ")) {
            return Optional.of(new Ok<>("\t\tcatch () {}\n"));
        } else {
            return Optional.empty();
        }
    }

    private static Optional<Result<String, CompileException>> compileDeclaration(String input) {
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

        return Optional.of(new Ok<>("\t\tlet " + name + " = 0;\n"));
    }

    interface Result<T, E extends Throwable> {
        T $() throws E;
    }

    record Ok<T, E extends Throwable>(T value) implements Result<T, E> {
        @Override
        public T $() {
            return value;
        }
    }

    record Err<T, E extends Throwable>(E value) implements Result<T, E> {
        @Override
        public T $() throws E {
            throw value;
        }
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
