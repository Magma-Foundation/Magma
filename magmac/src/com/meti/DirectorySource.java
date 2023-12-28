package com.meti;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class DirectorySource implements Source {
    private final Path root;
    private final String extension;

    public DirectorySource(Path root, String extension) {
        this.root = root;
        this.extension = extension;
    }

    @Override
    public Set<Path> collectSources() throws IOException {
        var set = new HashSet<Path>();

        Files.walkFileTree(root, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.getFileName().toString().endsWith("." + extension)) {
                    set.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        return set;
    }

    @Override
    public Stream<Unit> streamUnits() throws IOException {
        return collectSources()
                .stream()
                .map((Path source) -> new Unit(source, root));
    }
}
