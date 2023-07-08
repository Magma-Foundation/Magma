package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
        var lines = split(input);
        var builder = new StringBuilder();
        for (String line : lines) {
            var stripped = line.strip();
            if (!stripped.isEmpty() && !stripped.startsWith("package ")) {
                if (stripped.startsWith("import ")) {
                    var slice = stripped.substring("import ".length());
                    var separator = slice.lastIndexOf(".");
                    var parent = slice.substring(0, separator);
                    var child = slice.substring(separator + 1);
                    builder.append("import { ")
                            .append(child)
                            .append(" } from ")
                            .append(parent);

                } else {
                    throw new RuntimeException("Cannot compile: " + stripped);
                }
            }
        }
        return builder.toString();
    }

    private static List<String> split(String input) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == '}' && depth == 1) {
                builder.append('}');
                lines.add(builder.toString());
                builder = new StringBuilder();
                depth = 0;
            } else if(c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if(c == '{') depth++;
                if(c == '}') depth--;
                builder.append(c);
            }
        }
        lines.add(builder.toString());
        return lines
                .stream()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .collect(Collectors.toList());
    }
}
