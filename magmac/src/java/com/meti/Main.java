package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        var source = Paths.get(".", "magmac", "src", "java", "com", "meti", "Main.java");
        try {
            var input = Files.readString(source);
            var target = source.resolveSibling("Main.mgs");
            var outputContent = compile(input);
            var output = String.join("", outputContent);
            Files.writeString(target, output);
        } catch (IOException e) {
            throw new RuntimeException(source.toAbsolutePath().toString(), e);
        }
    }

    private static List<String> compile(String input) {
        var inputContent = split(input);

        return inputContent.stream()
                .map(String::strip)
                .map(Main::compileRootElement)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    private static Optional<String> compileRootElement(String stripped) {
        if (stripped.isEmpty() || stripped.startsWith("package ")) return Optional.empty();

        if (stripped.startsWith("import ")) {
            var segments = stripped.substring("import ".length());
            var separator = segments.lastIndexOf('.');
            var parent = segments.substring(0, separator);
            var child = segments.substring(separator + 1);
            return Optional.of("import { " + child + " } from " + parent + ";\n");
        }

        return Optional.of(stripped);
    }

    private static ArrayList<String> split(String input) {
        var current = new SplitState();
        for (int i = 0; i < input.length(); i++) {
            current = processChar(input.charAt(i), current);
        }

        return current.advance().lines;
    }

    private static SplitState processChar(char c, SplitState current) {
        if (c == ';' && current.depth == 0) return current.advance();
        return switch (c) {
            case '{' -> current.enter().append(c);
            case '}' -> current.exit().append(c);
            default -> current.append(c);
        };
    }

    private record SplitState(int depth, ArrayList<String> lines, StringBuilder builder) {
        public SplitState() {
            this(0, new ArrayList<>(), new StringBuilder());
        }

        private SplitState append(char c) {
            return new SplitState(depth, lines, this.builder.append(c));
        }

        private SplitState exit() {
            return new SplitState(depth - 1, lines, builder);
        }

        private SplitState enter() {
            return new SplitState(depth + 1, lines, builder);
        }

        private SplitState advance() {
            var copy = new ArrayList<>(lines);
            copy.add(builder.toString());
            return new SplitState(depth, copy, new StringBuilder());
        }
    }
}
