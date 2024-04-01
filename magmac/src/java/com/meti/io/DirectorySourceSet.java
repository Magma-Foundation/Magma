package com.meti.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

public record DirectorySourceSet(Path root) implements SourceSet {
    @Override
    public Set<PathSource> collect() throws IOException {
        try {
            try (var stream = Files.walk(root)) {
                return stream
                        .filter(Files::isRegularFile)
                        .filter(file -> file.toString().endsWith(".java"))
                        .map(file -> new PathSource(root, file))
                        .collect(Collectors.toSet());
            }
        } catch (Exception e) {
            throw new IOException("Failed to find source set at: " + root.toAbsolutePath(), e);
        }
    }
}
