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
            var source = Paths.get(".", "magmac", "src", "java", "com", "meti", "Main.java");
            var target = source.resolveSibling("Main.mgs");
            var input = Files.readString(source);
            var lines = split(input);

            var outputLines = new ArrayList<String>();
            for (var line : lines) {
                var stripped = line.strip();
                if (stripped.startsWith("package ")) continue;
                outputLines.add(compileImport(stripped)
                        .or(() -> compileClass(stripped))
                        .orElse(stripped));
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

        var instanceOutput = renderMagmaFunction(name, renderBlock(instanceMembers, 0), "export class ", 0, "", "");
        var renderedObject = staticMembers.isEmpty() ? "" : "export object " + name + " " + renderBlock(staticMembers, 0);
        return Optional.of(instanceOutput + renderedObject);
    }

    private static String renderBlock(ArrayList<String> members, int indent) {
        var blockString = members.stream()
                .map(member -> "\t".repeat(indent + 1) + member + "\n")
                .collect(Collectors.joining());

        return "{\n" + blockString + "\t".repeat(indent) + "}\n";
    }

    private static Optional<Result> compileMethod(String input) {
        var paramStart = input.indexOf('(');
        if (paramStart == -1) return Optional.empty();

        var paramEnd = input.indexOf(')');
        if (paramEnd == -1) return Optional.empty();

        var inputParamString = input.substring(paramStart + 1, paramEnd);

        String outputParamString;
        if (input.isBlank()) {
            outputParamString = inputParamString;
        } else {
            var space = inputParamString.lastIndexOf(' ');
            var type = inputParamString.substring(0, space);
            var name = inputParamString.substring(space + 1);
            outputParamString = name + " : " + type;
        }

        var before = input.substring(0, paramStart);
        var separator = before.lastIndexOf(' ');
        if (separator == -1) return Optional.empty();

        var flagsAndType = before.substring(0, separator);
        var typeSeparator = flagsAndType.lastIndexOf(' ');
        if (typeSeparator == -1) return Optional.empty();

        var modifiers = Arrays.asList(flagsAndType.substring(0, typeSeparator).strip().split(" "));
        var type = flagsAndType.substring(typeSeparator + 1);

        var name = before.substring(separator + 1);

        var contentStart = input.indexOf('{');
        if (contentStart == -1) return Optional.empty();

        var contentEnd = input.lastIndexOf('}');
        if (contentEnd == -1) return Optional.empty();

        var inputContent = input.substring(contentStart + 1, contentEnd);
        var inputContentLines = split(inputContent);
        var output = new ArrayList<String>();
        for (var inputContentLine : inputContentLines) {
            var line = inputContentLine.strip();
            if (!line.isEmpty()) {
                output.add(line + ";");
            }
        }

        var outputContent = renderBlock(output, 1);

        var modifierString = modifiers.contains("private") ? "private " : "";
        var rendered = renderMagmaFunction(name, outputContent, modifierString, 1, ": " + type, outputParamString);

        Result result;
        if (modifiers.contains("static")) {
            result = new StaticResult(rendered);
        } else {
            result = new InstanceResult(rendered);
        }

        return Optional.of(result);
    }

    private static String renderMagmaFunction(String name, String content, String modifiers, int indent, String typeString, String paramString) {
        return modifiers + "def " + name + "(" + paramString + ")" + typeString + " => " + content;
    }

    public static List<String> split(String input) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        var inQuotes = false;
        var wasEscaped = false;

        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == '\'') {
                if (!wasEscaped) {
                    inQuotes = !inQuotes;
                }
            }

            if (inQuotes) {
                builder.append(c);

                if (c == '\\') {
                    wasEscaped = true;
                }
            } else {
                if (c == ';' && depth == 0) {
                    lines.add(builder.toString());
                    builder = new StringBuilder();
                } else if (c == '}' && depth == 1) {
                    depth = 0;
                    builder.append('}');

                    lines.add(builder.toString());
                    builder = new StringBuilder();
                } else {
                    if (c == '{' || c == '(') depth++;
                    if (c == '}' || c == ')') depth--;
                    builder.append(c);
                }
            }
        }

        lines.add(builder.toString());
        lines.removeIf(String::isEmpty);
        return lines;
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
