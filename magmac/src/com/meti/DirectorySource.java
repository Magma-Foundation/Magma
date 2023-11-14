package com.meti;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record DirectorySource(Path root) implements Source {
    @Override
    public Set<Location> list() throws IOException {
        var files = new HashSet<Path>();
        Files.walkFileTree(root, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                files.add(file);
                return FileVisitResult.CONTINUE;
            }
        });
        return files.stream().map(Location::new).collect(Collectors.toSet());
    }
}
