package com.meti;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public record Compiler(String input) {
    private static String compileLine(String line) {
        return compileRecord(line)
                .or(() -> compileClass(line))
                .or(() -> compileImport(line))
                .orElse("");
    }

    private static Optional<String> compileRecord(String line) {
        if (!line.startsWith("record ")) return Optional.empty();
        var paramStart = line.indexOf('(');
        var name = line.substring("record ".length(), paramStart).strip();
        return Optional.of("class def " + name + "() => {}");
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