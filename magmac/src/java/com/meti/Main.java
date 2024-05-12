package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
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
            e.printStackTrace();
        }
    }

    private static void compile(String sourceExtension, String targetExtension) throws IOException, CompileException {
        var source = Paths.get(".", "magmac", "src", "java", "com", "meti", "Main." + sourceExtension);
        var input = Files.readString(source);

        var compileImpl = compile(input, sourceExtension, targetExtension);

        Files.writeString(source.resolveSibling("Main." + targetExtension), compileImpl);
    }

    private static String compile(String input, String sourceExtension, String targetExtension) throws CompileException {
        var lines = split(input);
        var output = new StringBuilder();
        for (var line : lines) {
            String str;
            if (sourceExtension.equals("java")) {
                str = compileJavaRoot(line);
            } else {
                str = compileMagmaRoot(line);
            }
            output.append(str);
        }

        return output.toString();
    }

    private static String compileMagmaRoot(String line) {
        var stripped = line.strip();
        if(stripped.startsWith("import ")) {
            var childStart = stripped.indexOf('{');
            var childEnd = stripped.indexOf('}');
            var child = stripped.substring(childStart + 1, childEnd).strip();
            var parent = stripped.substring(stripped.indexOf("from") + "from".length()).strip();
            return "import { " + child + " } from \"" + parent + "\";\n";
        } else {
            return "";
        }
    }

    private static ArrayList<String> split(String input) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;

        var inputLines = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        while (!inputLines.isEmpty()) {
            var c = inputLines.pop();

            if (c == ';' && depth == 0) {
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

    private static String compileJavaRoot(String input) throws CompileException {
        if (input.isBlank() || input.startsWith("package ")) return "";

        return compileImport(input)
                .or(() -> compileClass(input))
                .orElseThrow(() -> new CompileException(input));
    }

    private static Optional<String> compileClass(String input) {
        var contentStart = input.indexOf('{');
        if (contentStart == -1) return Optional.empty();

        var values = input.substring(0, contentStart).strip();
        var space = values.lastIndexOf(' ');
        if (space == -1) return Optional.empty();

        var name = values.substring(space + 1);

        return Optional.of("class def " + name + "() => {}");
    }

    private static Optional<String> compileImport(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("import ")) {
            var segments = stripped.substring("import ".length());
            var last = segments.lastIndexOf('.');
            var parent = segments.substring(0, last);
            var child = segments.substring(last + 1);
            return Optional.of("import { " + child + " } from " + parent + ";\n");
        }
        return Optional.empty();
    }
}