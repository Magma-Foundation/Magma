package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
                    Files.writeString(target, input);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
