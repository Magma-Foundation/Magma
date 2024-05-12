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

    private static Optional<Result<String, CompileException>> compileClass(String stripped) {
        var classIndex = stripped.indexOf("class");
        if (classIndex == -1) return Optional.empty();

        var contentStart = stripped.indexOf('{');
        if (contentStart == -1) return Optional.empty();

        var name = stripped.substring(classIndex + "class".length(), contentStart).strip();
        var modifierString = stripped.startsWith("public ") ? "export " : "";

        var contentEnd = stripped.lastIndexOf('}');
        if (contentEnd == -1) return Optional.empty();

        var content = stripped.substring(contentStart + 1, contentEnd).strip();
        var inputContent = split(content);
        var outputContent = new StringBuilder();
        for (String input1 : inputContent) {
            try {
                outputContent.append(compileClassMember(input1));
            } catch (CompileException e) {
                return Optional.of(new Err<>(e));
            }
        }

        return Optional.of(new Ok<>(modifierString + "class def " + name + "(){" + outputContent + "}"));
    }

    private static String compileClassMember(String input) throws CompileException {
        throw new CompileException("Unknown class member: " + input);
    }

    private static Optional<Result<String, CompileException>> compileImport(String stripped) {
        if (!stripped.startsWith("import ")) return Optional.empty();

        var segments = stripped.substring("import ".length());
        var separator = segments.lastIndexOf('.');
        var parent = segments.substring(0, separator);
        var child = segments.substring(separator + 1);
        return Optional.of(new Ok<>("import { " + child + " } from " + parent + ";\n"));
    }
}
