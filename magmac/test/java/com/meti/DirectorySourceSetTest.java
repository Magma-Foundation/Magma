package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Collectors;

class DirectorySourceSetTest {
    public static final Path ROOT = Paths.get(".", "temp");

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(ROOT, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.deleteIfExists(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.deleteIfExists(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Test
    void collect() throws IOException {
        Files.createDirectory(ROOT);

        var child = ROOT.resolve("test.txt");
        Files.createFile(child);

        var collected = new DirectorySourceSet(ROOT).collect().stream().map(value -> {
            return value.location();
        }).collect(Collectors.toSet());
        Assertions.assertTrue(collected.contains(child));
    }
}