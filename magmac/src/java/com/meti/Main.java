package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

public class Main {
    private static Optional<String> compileImport(String stripped) {
        if (stripped.startsWith("import ")) {
            var segments = stripped.substring("import ".length());
            var separator = segments.lastIndexOf('.');
            var parent = segments.substring(0, separator);
            var child = segments.substring(separator + 1);
            var rendered = "import { " + child + " } from " + parent + ";\n";
            return Optional.of(rendered);
        } else {
            return Optional.empty();
        }
    }

    public static void main(String[] args) {
        try {
            var source = Paths.get(".", "src", "java", "com", "meti", "Main.java");
            var target = source.resolveSibling("Main.mgs");
            var input = Files.readString(source);
            var lines = split(input);

            var outputLines = new ArrayList<String>();
            for (var line : lines) {
                var stripped = line.strip();
                if (!stripped.startsWith("package ")) {
                    outputLines.add(compileImport(stripped)
                            .or(() -> compileClass(stripped))
                            .orElse(stripped));
                }
            }

            var output = String.join("", outputLines);
            Files.writeString(target, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Optional<String> compileClass(String stripped) {
        var index = stripped.indexOf("class");
        if (index == -1) return Optional.empty();

        var after = stripped.substring(index + "class".length());

        var braceStart = after.indexOf('{');
        var name = after.substring(0, braceStart).strip();
        var inputContent = split(after.substring(braceStart + 1, after.lastIndexOf('}')));
        var outputContent = new ArrayList<String>();
        for (String s : inputContent) {
            outputContent.add(compileMethod(s).orElse(s));
        }

        return Optional.of(renderMagmaFunction(name, "{\n" + String.join("\n", outputContent) + "}", "export class ", 0));
    }

    private static Optional<String> compileMethod(String input) {
        var start = input.indexOf('(');
        if (start == -1) return Optional.empty();

        var before = input.substring(0, start);
        var separator = before.lastIndexOf(' ');
        var name = before.substring(separator + 1);

        var contentStart = input.indexOf('{');
        if(contentStart == -1) return Optional.empty();

        var contentEnd = input.lastIndexOf('}');
        if(contentEnd == -1) return Optional.empty();

        var content = input.substring(contentStart, contentEnd + 1);

        return Optional.of(renderMagmaFunction(name, content, "", 1));
    }

    private static String renderMagmaFunction(String name, String content, String modifiers, int indent) {
        return "\t".repeat(indent) + modifiers + "def " + name + "() => " + content;
    }

    private static ArrayList<String> split(String input) {
        var lines = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(buffer.toString());
                buffer = new StringBuilder();
            } else if (c == '}' && depth == 1) {
                buffer.append(c);
                depth = 0;
                lines.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                buffer.append(c);
            }
        }
        lines.add(buffer.toString());
        return lines;
    }
}
