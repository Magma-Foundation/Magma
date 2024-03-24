package com.meti;

import java.util.*;
import java.util.stream.Collectors;

public class Compiler {
    static String compile(String input) throws CompileException {
        var output = new ArrayList<String>();
        for (var line : split(input)) {
            output.add(compileLine(line.strip()));
        }

        return String.join("\n", output);
    }

    public static String[] split(String input) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
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
        lines.removeIf(String::isBlank);
        return lines.toArray(String[]::new);
    }

    private static String compileLine(String input) throws CompileException {
        var classResult = compileClass(input, 0);
        if (classResult.isPresent()) return classResult.get();

        var importResult = compileImport(input);
        if (importResult.isPresent()) return importResult.get();

        if (input.isEmpty()) {
            return "";
        }

        if (input.startsWith("package ")) {
            return "";
        }

        throw new CompileException("Invalid input: " + input);
    }

    private static Optional<String> compileClass(String input, int indent) {
        var bodyEnd = input.lastIndexOf('}');
        var classIndex = input.indexOf("class ");

        if (classIndex == -1 || bodyEnd != input.length() - 1) return Optional.empty();
        var name = input.substring(classIndex + "class ".length(), input.indexOf('{')).strip();
        var body = input.substring(input.indexOf('{') + 1, bodyEnd).strip();

        var compiledBody = compileClassBody(indent, body);
        var isPublic = input.startsWith("public ");
        var flagString = isPublic ? "export " : "";

        var bodyString = body.isEmpty() ? "{}" : "{" + compiledBody + "\n" + "\t".repeat(indent) + "}";
        return Optional.of(flagString + "class def " + name + "() => " + bodyString);
    }

    private static String compileClassBody(int indent, String body) {
        var builder = new StringBuilder();
        for (String s : split(body)) {
            builder.append("\n").append("\t".repeat(indent + 1)).append(compileStatement(indent, s).orElse(""));
        }

        return builder.toString();
    }

    private static Optional<String> compileStatement(int indent, String body) {
        if (body.isEmpty()) {
            return Optional.of("");
        } else {
            var definitionString = compileDefinition(indent, body);
            if (definitionString.isPresent()) return definitionString;

            return compileClass(body, indent + 1);
        }
    }

    private static Optional<String> compileDefinition(int indent, String body) {
        var valueSeparator = body.indexOf('=');
        if (valueSeparator == -1) return Optional.empty();

        var before = body.substring(0, valueSeparator).strip();
        var after = body.substring(valueSeparator + 1).strip();

        var nameSeparator = before.lastIndexOf(' ');
        if (nameSeparator == -1) return Optional.empty();

        var keys = before.substring(0, nameSeparator).strip();
        var typeSeparator = keys.lastIndexOf(' ');
        var type = typeSeparator == -1 ? keys : keys.substring(typeSeparator + 1).strip();

        Set<String> inputFlags;
        if (typeSeparator == -1) {
            inputFlags = Collections.emptySet();
        } else {
            inputFlags = Arrays.stream(keys.substring(0, typeSeparator).strip().split(" "))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .collect(Collectors.toSet());
        }

        var outputFlags = new ArrayList<String>();
        if (inputFlags.contains("public")) {
            outputFlags.add("pub");
        }

        if (inputFlags.contains("final")) {
            outputFlags.add("const");
        } else {
            outputFlags.add("let");
        }

        String outputType;
        if (type.equals("long")) {
            outputType = "I64";
        } else {
            outputType = "I32";
        }

        var name = before.substring(nameSeparator + 1).strip();
        var flagsString = String.join(" ", outputFlags);
        return Optional.of(flagsString + " " + name + " : " + outputType + " = " + after + ";");
    }

    private static Optional<String> compileImport(String input) {
        if (input.startsWith("import ")) {
            var isStatic = input.startsWith("import static ");
            var separator = input.lastIndexOf('.');

            var parentStart = isStatic ? "import static ".length() : "import ".length();
            var parentName = input.substring(parentStart, separator);

            var childName = input.substring(separator + 1);
            var childString = isStatic && childName.equals("*")
                    ? "*"
                    : "{ " + childName + " }";

            return Optional.of("import " + childString + " from " + parentName + ";");
        }
        return Optional.empty();
    }
}
