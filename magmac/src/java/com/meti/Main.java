package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    private static Optional<MapNode> lex(String input) {
        return new ImportLexer(input).lex();
    }

    private static Optional<String> render(MapNode node) {
        return Map.<String, Function<MapNode, ImportRenderer>>of("import", ImportRenderer::new)
                .get(node.name())
                .apply(node)
                .render();
    }

    public static void main(String[] args) {
        try {
            var source = Paths.get(".", "magmac", "src", "java", "com", "meti", "Main.java");
            var target = source.resolveSibling("Main.mgs");
            var input = Files.readString(source);
            var lines = Strings.split(input);

            var outputLines = new ArrayList<String>();
            for (var line : lines) {
                var stripped = line.strip();
                if (stripped.startsWith("package ")) continue;
                outputLines.add(lex(stripped)
                        .flatMap(Main::render)
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
        var inputContent = Strings.split(after.substring(braceStart + 1, after.lastIndexOf('}')));

        var members = getMultipleResult(inputContent, 0);

        var instanceOutput = renderMagmaFunction(name, "export class ", "", "", " => " + renderBlock(members.instanceMembers(), 0), "");
        var renderedObject = members.staticMembers().isEmpty() ? "" : "\nexport object " + name + " " + renderBlock(members.staticMembers(), 0);
        return Optional.of(instanceOutput + renderedObject);
    }

    private static MultipleResult getMultipleResult(List<String> inputContent, int indent) {
        var members = new MultipleResult(new ArrayList<>(), new ArrayList<>());

        for (var line : inputContent) {
            var result = compileInterface(line, indent)
                    .or(() -> compileRecord(line, indent))
                    .or(() -> compileMethod(line, indent))
                    .orElse(new InstanceResult(Collections.singletonList(line)));

            members.instanceMembers().addAll(result.instanceValue());
            members.staticMembers().addAll(result.staticValue());
        }
        return members;
    }

    private static Optional<? extends Result> compileRecord(String line, int indent) {
        if (!line.startsWith("record ")) return Optional.empty();

        var name = line.substring("record ".length(), line.indexOf('('));
        var paramString = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
        var compiledParameters = compileParamString(paramString);
        var split = Strings.split(line.substring(line.indexOf('{') + 1, line.lastIndexOf('}')));

        var results = getMultipleResult(split, indent);
        var renderedContent = renderBlock(results.instanceMembers(), indent + 1);

        return Optional.of(new InstanceResult(renderMagmaFunction(name, "class ", "", compiledParameters, " => " + renderedContent, "")));
    }

    private static String renderBlock(List<String> members, int indent) {
        var blockString = members.stream()
                .map(member -> "\t".repeat(indent + 1) + member + "\n")
                .collect(Collectors.joining());

        return "{\n" + blockString + "\t".repeat(indent) + "}";
    }

    private static Optional<Result> compileMethod(String input, int indent) {
        var paramStart = input.indexOf('(');
        if (paramStart == -1) return Optional.empty();

        var paramEnd = input.indexOf(')');
        if (paramEnd == -1) return Optional.empty();

        var inputParamString = input.substring(paramStart + 1, paramEnd);

        var outputParamString = compileParamString(inputParamString);

        var before = input.substring(0, paramStart);
        var annotationsSeparator = before.indexOf('\n');
        var before1 = annotationsSeparator == -1 ? before : before.substring(annotationsSeparator + 1).strip();

        var annotationsString = annotationsSeparator == -1 ? "" : before.substring(0, annotationsSeparator).strip();

        var separator = before1.lastIndexOf(' ');
        if (separator == -1) return Optional.empty();

        var flagsAndType = before1.substring(0, separator);
        var typeSeparator = flagsAndType.lastIndexOf(' ');
        if (typeSeparator == -1) return Optional.empty();

        var modifiers = Arrays.asList(flagsAndType.substring(0, typeSeparator).strip().split(" "));
        var type = flagsAndType.substring(typeSeparator + 1);

        var name = before1.substring(separator + 1);

        var contentStart = input.indexOf('{');
        var contentEnd = input.lastIndexOf('}');
        if (contentStart == -1 && contentEnd == -1) {
            var modifierString = modifiers.contains("private") ? "private " : "";
            var rendered = renderMagmaFunction(name, modifierString, ": " + type, outputParamString, ";", "");

            Result result;
            if (modifiers.contains("static")) {
                result = new StaticResult(rendered);
            } else {
                result = new InstanceResult(rendered);
            }

            return Optional.of(result);
        } else if (contentStart != -1 && contentEnd != -1) {
            var inputContent = input.substring(contentStart + 1, contentEnd);
            var inputContentLines = Strings.split(inputContent);
            var output = new ArrayList<String>();
            for (var inputContentLine : inputContentLines) {
                var line = inputContentLine.strip();
                if (!line.isEmpty()) {
                    var methodOutput = compileStatement(line, indent + 1);
                    output.add(methodOutput);
                }
            }

            var outputContent = renderBlock(output, indent + 1);

            var modifierString = modifiers.contains("private") ? "private " : "";
            var rendered = List.of(
                    annotationsString,
                    renderMagmaFunction(name, modifierString, ": " + type, outputParamString, " => " + outputContent, annotationsString)
            );

            Result result;
            if (modifiers.contains("static")) {
                result = new StaticResult(rendered);
            } else {
                result = new InstanceResult(rendered);
            }

            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }

    private static String compileParamString(String inputParamString) {
        var lines = inputParamString.split(",");
        return Arrays.stream(lines)
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .map(Main::compileDeclaration)
                .flatMap(Optional::stream)
                .map(Definition::render)
                .collect(Collectors.joining(", "));
    }

    private static String compileStatement(String line, int indent) {
        return compileIf(line, indent)
                .or(() -> compileElse(line, indent))
                .or(() -> compileFor(line, indent))
                .or(() -> compileTry(line, indent))
                .or(() -> compileDeclaration(line)
                        .map(Definition::render)
                        .map(value -> value + ";"))
                .or(() -> compileCatch(line, indent))
                .orElse(line + ";");
    }

    private static Optional<Result> compileInterface(String input, int indent) {
        if (!input.startsWith("interface ")) return Optional.empty();
        var start = input.indexOf('{');
        var end = input.lastIndexOf('}');

        var name = input.substring("interface ".length(), start).strip();
        var content = Strings.split(input.substring(start + 1, end));
        var members = getMultipleResult(content, indent);

        return Optional.of(new InstanceResult("struct " + name + " " + renderBlock(members.instanceMembers(), indent + 1)));
    }

    private static Optional<String> compileFor(String line, int indent) {
        if (!line.startsWith("for ")) return Optional.empty();

        var paramString = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
        String content = line.substring(line.indexOf('{') + 1, line.lastIndexOf('}'));
        var splitContent = Strings.split(content);
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
        cache.add(0, new Definition(declaration.get().name(), Optional.empty(), Optional.of(container + ".get(" + generatedName + ")")).render());

        var statements = renderBlock(cache, indent);
        return Optional.of("for (let " + generatedName + " = 0; i < " + container + ".size(); i++)" + statements);
    }

    private static Optional<String> compileIf(String line, int indent) {
        if (!line.startsWith("if")) return Optional.empty();

        var substring = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
        var compiled = compileValue(substring);

        var start = line.indexOf('{');
        var end = line.lastIndexOf('}');
        if (end == -1) {
            var value = line.substring(line.indexOf(')') + 1);
            var compiledValue = compileValue(value);
            return Optional.of("if (" + compiled + ")" + compiledValue + ";");
        }

        var content = line.substring(start + 1, end);
        var rendered = compileStatements(content, indent);

        return Optional.of("if (" + compiled + ")" + rendered);
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
        var splitContent = Strings.split(content);
        var builder = new ArrayList<String>();
        for (String s : splitContent) {
            var statement = compileStatement(s, indent);
            builder.add(statement);
        }
        return renderBlock(builder, indent);
    }

    private static Optional<String> compileElse(String line, int indent) {
        if (!line.startsWith("else ")) return Optional.empty();

        var blockStart = line.indexOf('{');
        var blockEnd = line.lastIndexOf('}');
        if (blockEnd == -1) {
            var value = line.substring("else ".length());
            var compiledValue = compileValue(value);
            return Optional.of("else " + compiledValue);
        }

        var content = line.substring(blockStart + 1, blockEnd);
        var splitContent = Strings.split(content);
        var builder = new ArrayList<String>();
        for (String s : splitContent) {
            var statement = compileStatement(s, indent + 1);
            builder.add(statement);
        }

        return Optional.of("else " + renderBlock(builder, indent));
    }

    private static Optional<String> compileTry(String line, int indent) {
        if (!line.startsWith("try ")) return Optional.empty();

        var content = line.substring(line.indexOf('{') + 1, line.lastIndexOf('}'));
        var splitContent = Strings.split(content);
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
            if (typeString.isEmpty()) return Optional.empty();
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
        var genStart = type.indexOf('<');
        var genEnd = type.lastIndexOf('>');
        if (genStart != -1 && genEnd != -1) {
            var parent = type.substring(0, genStart).strip();
            var substring = type.substring(genStart + 1, genEnd).strip();

            var parentType = compileType(parent);
            return parentType.map(s -> s + "<" + substring + ">");

        }

        if (type.equals("int")) return Optional.of("I32");
        if (!isAlphaNumeric(type)) return Optional.empty();
        else return Optional.of(type);
    }

    private static String renderMagmaFunction(String name, String modifiers, String typeString, String paramString, String contentString, String annotationsString) {
        return modifiers + "def " + name + "(" + paramString + ")" + typeString + contentString;
    }
}
