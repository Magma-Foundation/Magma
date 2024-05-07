package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    private static Optional<String> compileImport(String stripped) {
        if (!stripped.startsWith("import ")) return Optional.empty();

        var segments = stripped.substring("import ".length());
        var separator = segments.lastIndexOf('.');
        var parent = segments.substring(0, separator);
        var child = segments.substring(separator + 1);
        var rendered = "import { " + child + " } from " + parent + ";\n";
        return Optional.of(rendered);
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

        var instanceMembers = new ArrayList<String>();
        var staticMembers = new ArrayList<String>();
        for (var line : inputContent) {
            var result = compileMethod(line)
                    .orElse(new InstanceResult(line));

            result.instanceValue().ifPresent(instanceMembers::add);
            result.staticValue().ifPresent(staticMembers::add);
        }

        var instanceOutput = renderMagmaFunction(name, renderBlock(instanceMembers), "export class ", 0, "");
        var renderedObject = staticMembers.isEmpty() ? "" : "export object " + name + " " + renderBlock(staticMembers);
        return Optional.of(instanceOutput + renderedObject);
    }

    private static String renderBlock(ArrayList<String> instanceMembers) {
        return "{\n" + String.join("\n", instanceMembers) + "}\n";
    }

    private static Optional<Result> compileMethod(String input) {
        var start = input.indexOf('(');
        if (start == -1) return Optional.empty();

        var before = input.substring(0, start);
        var separator = before.lastIndexOf(' ');

        var flagsAndType = before.substring(0, separator);
        var typeSeparator = flagsAndType.lastIndexOf(' ');

        var modifiers = Arrays.asList(flagsAndType.substring(0, typeSeparator).strip().split(" "));
        var type = flagsAndType.substring(typeSeparator + 1);

        var name = before.substring(separator + 1);

        var contentStart = input.indexOf('{');
        if (contentStart == -1) return Optional.empty();

        var contentEnd = input.lastIndexOf('}');
        if (contentEnd == -1) return Optional.empty();

        var content = input.substring(contentStart, contentEnd + 1);

        var modifierString = modifiers.contains("private") ? "private " : "";
        var rendered = renderMagmaFunction(name, content, modifierString, 1, ": " + type);

        Result result;
        if (modifiers.contains("static")) {
            result = new StaticResult(rendered);
        } else {
            result = new InstanceResult(rendered);
        }

        return Optional.of(result);
    }

    private static String renderMagmaFunction(String name, String content, String modifiers, int indent, String typeString) {
        return "\t".repeat(indent) + modifiers + "def " + name + "()" + typeString + " => " + content;
    }

    private static List<String> split(String input) {
        var lines = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;
        var inSingleString = false;

        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if(inSingleString) {
                buffer.append(c);

                if(c == '\'') inSingleString = false;
            } else {
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
                    if(c == '\'') inSingleString = true;

                    buffer.append(c);
                }
            }
        }
        lines.add(buffer.toString());

        return lines.stream()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .collect(Collectors.toList());
    }

    interface Result {
        Optional<String> staticValue();

        Optional<String> instanceValue();
    }

    record StaticResult(String value) implements Result {
        @Override
        public Optional<String> staticValue() {
            return Optional.of(value);
        }

        @Override
        public Optional<String> instanceValue() {
            return Optional.empty();
        }
    }

    record InstanceResult(String value) implements Result {
        @Override
        public Optional<String> staticValue() {
            return Optional.empty();
        }

        @Override
        public Optional<String> instanceValue() {
            return Optional.of(value);
        }
    }
}
