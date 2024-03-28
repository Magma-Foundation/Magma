package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

public class DirectorySourceSet implements SourceSet {
    private final Path root;

    public DirectorySourceSet(Path root) {
        this.root = root;
    }

    @Override
    public Set<Path> collect() throws IOException {
        try (var stream = Files.walk(root)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".java"))
                    .collect(Collectors.toSet());
        }
    }
}
