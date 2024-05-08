package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    private static Option<MapNode> lex(String input) {
        return new ImportLexer(input).lex();
    }

    private static Option<String> render(MapNode node) {
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
                        .orLazy(() -> compileClass(stripped))
                        .orElse(stripped));
            }

            var output = String.join("", outputLines);
            Files.writeString(target, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Option<String> compileClass(String stripped) {
        var index = stripped.indexOf("class");
        if (index == -1) return new None<>();

        var after = stripped.substring(index + "class".length());

        var braceStart = after.indexOf('{');
        var name = after.substring(0, braceStart).strip();
        var inputContent = Strings.split(after.substring(braceStart + 1, after.lastIndexOf('}')));

        var members = getMultipleResult(inputContent, 0);

        var instanceOutput = renderMagmaFunction(name, "export class ", "", "", " => " + renderBlock(members.instanceMembers(), 0), "");
        var renderedObject = members.staticMembers().isEmpty() ? "" : "\nexport object " + name + " " + renderBlock(members.staticMembers(), 0);
        return new Some<>(instanceOutput + renderedObject);
    }

    private static MultipleResult getMultipleResult(List<String> inputContent, int indent) {
        var members = new MultipleResult(new ArrayList<>(), new ArrayList<>());

        for (var line : inputContent) {
            var result = compileInterface(line, indent)
                    .orLazy(() -> compileRecord(line, indent))
                    .orLazy(() -> compileMethod(line, indent))
                    .orElse(new InstanceResult(Collections.singletonList(line)));

            members.instanceMembers().addAll(result.instanceValue());
            members.staticMembers().addAll(result.staticValue());
        }
        return members;
    }

    private static Option<Result> compileRecord(String line, int indent) {
        if (!line.startsWith("record ")) return new None<>();

        var name = line.substring("record ".length(), line.indexOf('('));
        var paramString = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
        var compiledParameters = compileParamString(paramString);
        var split = Strings.split(line.substring(line.indexOf('{') + 1, line.lastIndexOf('}')));

        var results = getMultipleResult(split, indent);
        var renderedContent = renderBlock(results.instanceMembers(), indent + 1);

        return new Some<>(new InstanceResult(renderMagmaFunction(name, "class ", "", compiledParameters, " => " + renderedContent, "")));
    }

    private static String renderBlock(List<String> members, int indent) {
        var blockString = members.stream()
                .map(member -> "\t".repeat(indent + 1) + member + "\n")
                .collect(Collectors.joining());

        return "{\n" + blockString + "\t".repeat(indent) + "}";
    }

    private static Option<Result> compileMethod(String input, int indent) {
        var paramStart = input.indexOf('(');
        if (paramStart == -1) return new None<>();

        var paramEnd = input.indexOf(')');
        if (paramEnd == -1) return new None<>();

        var inputParamString = input.substring(paramStart + 1, paramEnd);

        var outputParamString = compileParamString(inputParamString);

        var before = input.substring(0, paramStart);
        var annotationsSeparator = before.indexOf('\n');
        var before1 = annotationsSeparator == -1 ? before : before.substring(annotationsSeparator + 1).strip();

        var annotationsString = annotationsSeparator == -1 ? "" : before.substring(0, annotationsSeparator).strip();

        var separator = before1.lastIndexOf(' ');
        if (separator == -1) return new None<>();

        var flagsAndType = before1.substring(0, separator);
        var typeSeparator = flagsAndType.lastIndexOf(' ');
        if (typeSeparator == -1) return new None<>();

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

            return new Some<>(result);
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

            return new Some<>(result);
        } else {
            return new None<>();
        }
    }

    private static String compileParamString(String inputParamString) {
        var lines = inputParamString.split(",");
        return Arrays.stream(lines)
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .map(Main::compileDeclaration)
                .flatMap(Options::stream)
                .map(Definition::render)
                .collect(Collectors.joining(", "));
    }

    private static String compileStatement(String line, int indent) {
        return compileIf(line, indent)
                .orLazy(() -> compileElse(line, indent))
                .orLazy(() -> compileFor(line, indent))
                .orLazy(() -> compileTry(line, indent))
                .orLazy(() -> compileDeclaration(line)
                        .map(Definition::render)
                        .map(value -> value + ";"))
                .orLazy(() -> compileCatch(line, indent))
                .orElse(line + ";");
    }

    private static Option<Result> compileInterface(String input, int indent) {
        if (!input.startsWith("interface ")) return new None<>();
        var start = input.indexOf('{');
        var end = input.lastIndexOf('}');

        var name = input.substring("interface ".length(), start).strip();
        var content = Strings.split(input.substring(start + 1, end));
        var members = getMultipleResult(content, indent);

        return new Some<>(new InstanceResult("struct " + name + " " + renderBlock(members.instanceMembers(), indent + 1)));
    }

    private static Option<String> compileFor(String line, int indent) {
        if (!line.startsWith("for ")) return new None<>();

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
            return new Some<>("for (" + paramString + ")" + statements);
        }

        var substring = paramString.substring(0, separator);
        var declaration = compileDeclaration(substring);
        if (declaration.isEmpty()) return new None<>();

        var container = paramString.substring(separator + 1).strip();
        var generatedName = "__temp__";
        cache.add(0, new Definition(declaration.orElseNull().name(), new None<>(), new Some<>(container + ".get(" + generatedName + ")")).render());

        var statements = renderBlock(cache, indent);
        return new Some<>("for (let " + generatedName + " = 0; i < " + container + ".size(); i++)" + statements);
    }

    private static Option<String> compileIf(String line, int indent) {
        if (!line.startsWith("if")) return new None<>();

        var substring = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
        var compiled = compileValue(substring);

        var start = line.indexOf('{');
        var end = line.lastIndexOf('}');
        if (end == -1) {
            var value = line.substring(line.indexOf(')') + 1);
            var compiledValue = compileValue(value);
            return new Some<>("if (" + compiled + ")" + compiledValue + ";");
        }

        var content = line.substring(start + 1, end);
        var rendered = compileStatements(content, indent);

        return new Some<>("if (" + compiled + ")" + rendered);
    }

    private static Option<String> compileCatch(String line, int indent) {
        if (!line.startsWith("catch ")) return new None<>();

        var substring = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
        var compiled = compileDeclaration(substring)
                .map(Definition::render)
                .orElse(substring);

        var content = line.substring(line.indexOf('{') + 1, line.lastIndexOf('}'));
        var rendered = compileStatements(content, indent);

        return new Some<>("catch (" + compiled + ")" + rendered);
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

    private static Option<String> compileElse(String line, int indent) {
        if (!line.startsWith("else ")) return new None<>();

        var blockStart = line.indexOf('{');
        var blockEnd = line.lastIndexOf('}');
        if (blockEnd == -1) {
            var value = line.substring("else ".length());
            var compiledValue = compileValue(value);
            return new Some<>("else " + compiledValue);
        }

        var content = line.substring(blockStart + 1, blockEnd);
        var splitContent = Strings.split(content);
        var builder = new ArrayList<String>();
        for (String s : splitContent) {
            var statement = compileStatement(s, indent + 1);
            builder.add(statement);
        }

        return new Some<>("else " + renderBlock(builder, indent));
    }

    private static Option<String> compileTry(String line, int indent) {
        if (!line.startsWith("try ")) return new None<>();

        var content = line.substring(line.indexOf('{') + 1, line.lastIndexOf('}'));
        var splitContent = Strings.split(content);
        var builder = new ArrayList<String>();
        for (String s : splitContent) {
            var statement = compileStatement(s, indent + 1);
            builder.add(statement);
        }

        return new Some<>("try " + renderBlock(builder, indent));
    }

    private static Option<Definition> compileDeclaration(String line) {
        var valueSeparator = line.indexOf('=');

        var before = line.substring(0, valueSeparator == -1 ? line.length() : valueSeparator).strip();
        var lastSpace = before.lastIndexOf(' ');
        if (lastSpace == -1) {
            return new None<>();
        }

        var type = before.substring(0, lastSpace).strip();

        var definitionName = before.substring(lastSpace).strip();
        if (!isAlphaNumeric(definitionName)) return new None<>();

        Option<String> typeString;
        if (type.equals("var")) typeString = new None<>();
        else {
            typeString = compileType(type);
            if (typeString.isEmpty()) return new None<>();
        }

        Option<String> value;
        if (valueSeparator != -1) {
            var stripped = line.substring(valueSeparator + 1).strip();
            value = new Some<>(compileValue(stripped));
        } else {
            value = new None<>();
        }

        return new Some<>(new Definition(definitionName, typeString, value));
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

    private static Option<String> compileType(String type) {
        var genStart = type.indexOf('<');
        var genEnd = type.lastIndexOf('>');
        if (genStart != -1 && genEnd != -1) {
            var parent = type.substring(0, genStart).strip();
            var substring = type.substring(genStart + 1, genEnd).strip();

            var parentType = compileType(parent);
            return parentType.map(s -> s + "<" + substring + ">");
        }

        return extracted(type).orLazy(() -> {
            return lexStringType(type);
        }).flatMap(node -> new PrimitiveRenderer(node).render());
    }

    private static Option<MapNode> extracted(String type) {
        return type.equals("int")
                ? new Some<>(new MapNode("primitive-type", Map.of("value", "I32")))
                : new None<>();
    }

    private static Option<MapNode> lexStringType(String type) {
        return isAlphaNumeric(type)
                ? new Some<>(new MapNode("primitive-type", Map.of("value", type)))
                : new None<>();
    }

    private static String renderMagmaFunction(String name, String modifiers, String typeString, String paramString, String contentString, String annotationsString) {
        return modifiers + "def " + name + "(" + paramString + ")" + typeString + contentString;
    }
}
