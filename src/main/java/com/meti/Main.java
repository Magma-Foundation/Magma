package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        var sourceDirectory = Paths.get(".", "src", "main", "java");
        var targetDirectory = Paths.get(".", "target");

        try (var sourcesStream = Files.walk(sourceDirectory)) {
            var sources = sourcesStream
                    .filter(path -> path.toString().endsWith(".java"))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toSet());

            sources.forEach(source -> {
                try {
                    var input = Files.readString(source);
                    var relative = sourceDirectory.relativize(source);
                    var target = targetDirectory.resolve(relative);
                    var parent = target.getParent();
                    if (!Files.exists(parent)) {
                        Files.createDirectories(parent);
                    }
                    Files.writeString(target, compile(input));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        var lines = new Splitter(input).split();
        var builder = new StringBuilder();
        for (var line : lines) {
            var stripped = line.strip();
            if (!stripped.isEmpty() && !stripped.startsWith("package ")) {
                var str = compileImport(stripped)
                        .or(() -> compileClass(stripped))
                        .orElseThrow(() -> new RuntimeException("Cannot compile: " + stripped));
                builder.append(str);
            }
        }
        return builder.toString();
    }

    private static Optional<String> compileClass(String input) {
        var index = input.indexOf(" class ");
        var bodyStart = input.indexOf('{');
        if (index == -1 || bodyStart == -1) {
            return Optional.empty();
        }

        var name = input.substring(index + " class ".length(), bodyStart).strip();
        return Optional.of("class def " + name + "() => {}");
    }

    private static Optional<String> compileImport(String stripped) {
        if (stripped.startsWith("import ")) {
            var slice = stripped.substring("import ".length());
            var separator = slice.lastIndexOf(".");
            var parent = slice.substring(0, separator);
            var child = slice.substring(separator + 1);
            var format = "import { %s } from %s";
            var message = format.formatted(child, parent);
            return Optional.of(message);
        } else {
            return Optional.empty();
        }
    }

}
