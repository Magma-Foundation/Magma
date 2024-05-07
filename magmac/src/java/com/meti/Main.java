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

        var instanceOutput = renderMagmaFunction(name, renderBlock(instanceMembers, 0), "export class ", "", "");
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
                var methodOutput = compileStatement(line, 2);
                output.add(methodOutput);
            }
        }

        var outputContent = renderBlock(output, 1);

        var modifierString = modifiers.contains("private") ? "private " : "";
        var rendered = renderMagmaFunction(name, outputContent, modifierString, ": " + type, outputParamString);

        Result result;
        if (modifiers.contains("static")) {
            result = new StaticResult(rendered);
        } else {
            result = new InstanceResult(rendered);
        }

        return Optional.of(result);
    }

    private static String compileStatement(String line, int indent) {
        return compileFor(line, indent)
                .or(() -> compileTry(line, indent))
                .or(() -> compileDeclaration(line)
                        .map(Definition::render)
                        .map(value -> value + ";"))
                .or(() -> compileCatch(line, indent))
                .orElse(line + ";");
    }

    private static Optional<String> compileFor(String line, int indent) {
        if (!line.startsWith("for ")) return Optional.empty();

        var paramString = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
        String content = line.substring(line.indexOf('{') + 1, line.lastIndexOf('}'));
        var splitContent = split(content);
        var cache = new ArrayList<String>();
        for (String s : splitContent) {
            var statement = compileStatement(s, indent);
            cache.add(statement);
        }

        var separator = paramString.indexOf(':');
        if (separator == -1) {
            var statements = renderBlock(cache, indent);
            return Optional.of("for (" + paramString + ")" + statements);
        }

        var substring = paramString.substring(0, separator);
        var declaration = compileDeclaration(substring);
        if (declaration.isEmpty()) return Optional.empty();

        var container = paramString.substring(separator + 1).strip();
        var generatedName = "__temp__";
        cache.add(0, new Definition(declaration.get().name, Optional.empty(), Optional.of(container + ".get(" + generatedName + ")")).render());

        var statements = renderBlock(cache, indent);
        return Optional.of("for (let " + generatedName + " = 0; i < " + container + ".size(); i++)" + statements);
    }

    private static Optional<String> compileCatch(String line, int indent) {
        if (!line.startsWith("catch ")) return Optional.empty();

        var substring = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
        var compiled = compileDeclaration(substring)
                .map(Definition::render)
                .orElse(substring);

        var content = line.substring(line.indexOf('{') + 1, line.lastIndexOf('}'));
        var rendered = compileStatements(content, indent);

        return Optional.of("catch (" + compiled + ")" + rendered);
    }

    private static String compileStatements(String content, int indent) {
        var splitContent = split(content);
        var builder = new ArrayList<String>();
        for (String s : splitContent) {
            var statement = compileStatement(s, indent);
            builder.add(statement);
        }
        return renderBlock(builder, indent);
    }

    private static Optional<String> compileTry(String line, int indent) {
        if (!line.startsWith("try ")) return Optional.empty();

        var content = line.substring(line.indexOf('{') + 1, line.lastIndexOf('}'));
        var splitContent = split(content);
        var builder = new ArrayList<String>();
        for (String s : splitContent) {
            var statement = compileStatement(s, indent + 1);
            builder.add(statement);
        }

        return Optional.of("try " + renderBlock(builder, indent));
    }

    private static Optional<Definition> compileDeclaration(String line) {
        var valueSeparator = line.indexOf('=');

        var before = line.substring(0, valueSeparator == -1 ? line.length() : valueSeparator).strip();
        var lastSpace = before.lastIndexOf(' ');
        if (lastSpace == -1) {
            return Optional.empty();
        }

        var type = before.substring(0, lastSpace).strip();

        var definitionName = before.substring(lastSpace).strip();
        if (!isAlphaNumeric(definitionName)) return Optional.empty();

        Optional<String> typeString;
        if (type.equals("var")) typeString = Optional.empty();
        else {
            typeString = compileType(type);
            if(typeString.isEmpty()) return Optional.empty();
        }

        Optional<String> value;
        if (valueSeparator != -1) {
            var stripped = line.substring(valueSeparator + 1).strip();
            value = Optional.of(compileValue(stripped));
        } else {
            value = Optional.empty();
        }

        return Optional.of(new Definition(definitionName, typeString, value));
    }

    private static String compileValue(String stripped) {
        String outputValue;
        if (stripped.startsWith("new ")) {
            outputValue = stripped.substring("new ".length());
        } else {
            outputValue = stripped;
        }
        return outputValue;
    }

    private static boolean isAlphaNumeric(String definitionName) {
        for (int i = 0; i < definitionName.length(); i++) {
            var c = definitionName.charAt(i);
            if (!Character.isAlphabetic(c) && !Character.isDigit(c)) return false;
        }
        return true;
    }

    private static Optional<String> compileType(String type) {
        if (!isAlphaNumeric(type)) return Optional.empty();
        else return Optional.of(type);
    }

    private static String renderMagmaFunction(String name, String content, String modifiers, String typeString, String paramString) {
        return modifiers + "def " + name + "(" + paramString + ")" + typeString + " => " + content;
    }

    public static List<String> split(String input) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;

        var inSingleQuotes = false;
        var inDoubleQuotes = false;

        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == '\'') {
                inSingleQuotes = !inSingleQuotes;
            }

            if (c == '\"') {
                inDoubleQuotes = !inDoubleQuotes;
            }

            if (inSingleQuotes) {
                builder.append(c);
            } else if (inDoubleQuotes) {
                builder.append(c);
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
                    if (c == '{' || c == '(') {
                        depth++;
                    }
                    if (c == '}' || c == ')') {
                        depth--;
                    }
                    builder.append(c);
                }
            }
        }

        lines.add(builder.toString());
        return lines.stream()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .collect(Collectors.toList());
    }

    interface Result {
        Optional<String> staticValue();

        Optional<String> instanceValue();
    }

    record Definition(String name, Optional<String> type, Optional<String> value) {
        String render() {
            var typeString = type.map(type -> " : " + type).orElse("");
            var valueString = value.map(value -> " = " + value).orElse("");
            return "let " + name + typeString + valueString;
        }
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
