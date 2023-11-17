package com.meti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Compiler(String input) {
    private static String compileType(String inputType) {
        if (inputType.equals("void")) {
            return "Void";
        }
        return inputType;
    }

    private static Stream<String> stream(String content) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < content.length(); i++) {
            var c = content.charAt(i);
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
        return lines.stream();
    }

    private static Optional<String> compileImport(String input) {
        if (input.startsWith("import ")) {
            var slice = input.substring("import ".length());
            var importSeparator = slice.indexOf('.');
            var parent = slice.substring(0, importSeparator);
            var child = slice.substring(importSeparator + 1);
            return Optional.of("import { " + child + " } from " + parent + ";");
        }
        return Optional.empty();
    }

    private static Optional<String> compileMethod(String input) {
        var separator = input.indexOf(' ');
        var end = input.indexOf('(');
        if (end == -1) {
            return Optional.empty();
        }

        var inputType = input.substring(0, separator).strip();
        var name = input.substring(separator + 1, end).strip();

        var throwsIndex = input.indexOf("throws");
        String throwsExtra;
        if (throwsIndex != -1) {
            throwsExtra = " ? " + input.substring(throwsIndex + "throws".length()).strip();
        } else {
            throwsExtra = "";
        }
        return Optional.of("def " + name + "() : " + compileType(inputType) + throwsExtra + ";");
    }

    String compile() {
        return stream(this.input).map(String::strip).filter(value -> !value.isEmpty()).map(this::compileLine).collect(Collectors.joining());
    }

    private String compileLine(String input) {
        if (input.isEmpty() || input.startsWith("package ")) {
            return "";
        }

        return Stream.<Supplier<Optional<String>>>of(
                () -> compileRecord(input),
                () -> compileClass(input),
                () -> compileInterface(input),
                () -> compileBlock(input),
                () -> compileImport(input),
                () -> compileMethod(input)).map(Supplier::get).flatMap(Optional::stream).findFirst().orElse(input);
    }

    private Optional<String> compileRecord(String input) {
        if (input.startsWith("record ")) {
            var name = input.substring("record ".length(), input.indexOf('(')).strip();
            return Optional.of("class def " + name + "() => {}");
        } else {
            return Optional.empty();
        }
    }

    private Optional<String> compileClass(String input) {
        if (input.startsWith("class ")) {
            var name = input.substring("class ".length(), input.indexOf('{')).strip();
            return Optional.of("class def " + name + "() => {}");
        } else {
            return Optional.empty();
        }
    }

    private Optional<String> compileInterface(String input) {
        var index = input.indexOf("interface ");
        if (index == -1) {
            return Optional.empty();
        }
        var keys = Arrays.stream(input.substring(0, index).strip().split(" ")).map(String::strip).filter(value -> !value.isEmpty()).collect(Collectors.toSet());

        var start = index + "interface ".length();
        var braceStart = input.indexOf('{', start);
        if (braceStart == -1) {
            return Optional.empty();
        }

        var name = input.substring(start, braceStart).strip();
        var content = input.substring(braceStart).strip();
        var keyString = keys.size() == 0 ? "" : String.join(" ", keys) + " ";
        return Optional.of(keyString + "trait " + name + " " + compileLine(content));
    }

    public Optional<String> compileBlock(String input) {
        if (input.startsWith("{")) {
            var content = input.substring(1, input.length() - 1).strip();
            return Optional.of(stream(content).map(this::compileLine).collect(Collectors.joining("", "{", "}")));
        }
        return Optional.empty();
    }
}