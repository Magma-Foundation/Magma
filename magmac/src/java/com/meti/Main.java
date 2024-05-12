package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        try {
            compile("java", "mgs");
            compile("mgs", "js");
            compile("mgs", "d.ts");
            compile("mgs", "c");
            compile("mgs", "h");
        } catch (IOException | CompileException e) {
            throw new RuntimeException(e);
        }
    }

    private static void compile(String sourceEnv, String targetEnv) throws IOException, CompileException {
        var source = Paths.get(".", "magmac", "src", "java", "com", "meti", "Main." + sourceEnv);
        var input = Files.readString(source);
        var target = source.resolveSibling("Main." + targetEnv);
        Files.writeString(target, compile(input));
    }

    private static String compile(String input) throws CompileException {
        var lines = split(input);

        var output = new StringBuilder();
        for (String line : lines) {
            output.append(compileRoot(line));
        }

        return output.toString();
    }

    private static List<String> split(String input) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        var queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        while (!queue.isEmpty()) {
            var c = queue.pop();

            if (c == '\'') {
                builder.append(c);
                var next = queue.pop();
                builder.append(next);
                if (next == '\\') {
                    builder.append(queue.pop());
                }

                builder.append(queue.pop());
                continue;
            }

            if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else if (c == '}' && depth == 1) {
                builder.append(c);
                depth = 0;

                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }

        lines.add(builder.toString());
        return lines;
    }

    private static String compileRoot(String line) throws CompileException {
        var stripped = line.strip();

        if (stripped.isEmpty() || stripped.startsWith("package ")) return "";
        return compileImport(stripped)
                .or(() -> compileClass(stripped))
                .orElseGet(() -> new Err<>(new CompileException(line)))
                .$();
    }

    private static Optional<Result<String, CompileException>> compileClass(String input) {
        var classIndex = input.indexOf("class");
        if (classIndex == -1) return Optional.empty();

        var contentStart = input.indexOf('{');
        if (contentStart == -1) return Optional.empty();

        var name = input.substring(classIndex + "class".length(), contentStart).strip();
        var modifierString = input.startsWith("public ") ? "export " : "";

        var contentEnd = input.lastIndexOf('}');
        if (contentEnd == -1) return Optional.empty();

        var content = input.substring(contentStart + 1, contentEnd).strip();
        var inputContent = split(content);

        return compileClassMembers(inputContent)
                .mapErr(err -> new CompileException("Failed to compile class body: " + input, err))
                .mapValue(output -> Optional.of(modifierString + "class def " + name + "(){\n" + output + "}"))
                .into(Results::unwrapOptional);

    }

    private static Result<String, CompileException> compileClassMembers(List<String> inputContent) {
        var outputContent = new StringBuilder();
        for (var input : inputContent) {
            if (input.isBlank()) continue;

            try {
                outputContent.append(compileClassMember(input));
            } catch (CompileException e) {
                return new Err<>(e);
            }
        }

        return new Ok<>(outputContent.toString());
    }

    private static String compileClassMember(String input) throws CompileException {
        return compileMethod(input).orElseThrow(() -> new CompileException("Unknown class member: " + input));
    }

    private static Optional<String> compileMethod(String input) {
        var paramStart = input.indexOf('(');
        if (paramStart == -1) return Optional.empty();

        var paramEnd = input.indexOf(')');
        if (paramEnd == -1) return Optional.empty();

        var paramString = input.substring(paramStart + 1, paramEnd);
        var paramStrings = List.of(paramString.split(","));
        var outputParams = Optional.<StringBuilder>empty();
        for (String string : paramStrings) {
            if (string.isBlank()) continue;

            var strippedParam = string.strip();
            var separator = strippedParam.lastIndexOf(' ');
            var type = strippedParam.substring(0, separator);
            var name = strippedParam.substring(separator + 1);

            var next = name + " : " + type;
            outputParams = Optional.of(outputParams.map(value -> value.append(", ").append(next))
                    .orElse(new StringBuilder(next)));
        }

        var before = input.substring(0, paramStart).strip();
        var separator = before.lastIndexOf(' ');
        var name = before.substring(separator + 1);
        var renderedParams = outputParams.orElse(new StringBuilder());

        return Optional.of("\tdef " + name + "(" +
                           renderedParams +
                           ") => {}\n");
    }

    private static Optional<Result<String, CompileException>> compileImport(String stripped) {
        if (!stripped.startsWith("import ")) return Optional.empty();

        var segments = stripped.substring("import ".length());
        var separator = segments.lastIndexOf('.');
        if (separator == -1) return Optional.empty();

        var parent = segments.substring(0, separator);
        var child = segments.substring(separator + 1);
        return Optional.of(new Ok<>("import { " + child + " } from " + parent + ";\n"));
    }
}
