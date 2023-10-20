package com.meti;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public record Compiler(String input) {
    private static String compileLine(String line) {
        return compilePackage(line)
                .or(() -> compileBody(line))
                .or(() -> compileRecord(line))
                .or(() -> compileClass(line))
                .or(() -> compileImport(line))
                .or(() -> compileMethod(line))
                .orElseThrow(() -> new RuntimeException("Unknown input '" + line + "'."));
    }

    private static Optional<String> compilePackage(String line) {
        if (line.startsWith("package ")) {
            return Optional.of("");
        } else {
            return Optional.empty();
        }
    }

    private static Optional<String> compileBody(String line) {
        if (line.startsWith("{") && line.endsWith("}")) {
            var slice = line.substring(1, line.length() - 1).strip();
            String compiledSlice;
            if (slice.isEmpty()) {
                compiledSlice = "";
            } else {
                compiledSlice = compileLine(slice);
            }
            return Optional.of("{" + compiledSlice + "}");
        } else {
            return Optional.empty();
        }
    }

    private static Optional<String> compileMethod(String line) {
        var nameStart = line.indexOf(' ');
        if (nameStart == -1) {
            return Optional.empty();
        }

        var nameEnd = line.indexOf('(');
        if (nameEnd == -1) {
            return Optional.empty();
        }

        var name = line.substring(nameStart + 1, nameEnd).strip();
        return Optional.of("def " + name + "() => {}");
    }

    private static Optional<String> compileRecord(String line) {
        if (!line.contains("record ")) return Optional.empty();
        var keywordStart = line.indexOf("record ");
        var keywords = line.substring(0, keywordStart);

        var paramStart = line.indexOf('(');
        var parameterOutput = compileParameters(line.substring(paramStart + 1, line.indexOf(')')).strip());

        var name = line.substring(keywordStart + "record ".length(), paramStart).strip();
        var prefix = keywords.equals("public ") ? "export " : "";

        var bodyStart = line.indexOf('{');
        var bodyEnd = line.indexOf('}');
        var bodySlice = line.substring(bodyStart, bodyEnd + 1).strip();
        var body = compileLine(bodySlice);

        return Optional.of(prefix + "class def " + name + "(" + parameterOutput + ") => " + body);
    }

    private static String compileParameters(String parameterString) {
        if (parameterString.isEmpty()) {
            return "";
        } else {
            var parameterSeparator = parameterString.indexOf(' ');
            var type = parameterString.substring(0, parameterSeparator).strip();
            var parameterName = parameterString.substring(parameterSeparator + 1).strip();
            return parameterName + " : " + type;
        }
    }

    private static Optional<String> compileClass(String line) {
        if (!line.startsWith("class ")) return Optional.empty();
        var bodyStart = line.indexOf('{');
        var className = line.substring("class ".length(), bodyStart).strip();
        return Optional.of("class def " + className + "() => {}");
    }

    private static Optional<String> compileImport(String line) {
        if (!line.startsWith("import ")) return Optional.empty();
        var segments = line.substring("import ".length()).strip();
        var separator = segments.lastIndexOf(".");
        var parent = segments.substring(0, separator).strip();
        var child = segments.substring(separator + 1).strip();
        return Optional.of("import { " + child + " } from " + parent + ";");
    }

    String compile() {
        return Arrays.stream(input.split(";"))
                .map(Compiler::compileLine)
                .collect(Collectors.joining());
    }
}