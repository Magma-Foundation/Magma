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
    public Set<PathSource> collect() throws IOException {
        Set<Path> result;
        try (var stream = Files.walk(root)) {
            result = stream.collect(Collectors.toSet());
        }
        return result
                .stream()
                .map((Path child) -> new PathSource(root, child))
                .collect(Collectors.toSet());
    }
}
