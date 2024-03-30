package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class DirectorySourceSetTest {
    public static final Path ROOT = Paths.get(".", "temp");

    @BeforeEach
    void setUp () throws IOException {
        Files.createDirectories(ROOT);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(ROOT, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Test
    void collect() throws IOException {
        var child = ROOT.resolve("ApplicationTest.java");
        Files.createFile(child);

        var actual = new DirectorySourceSet(ROOT).collect();
        assertIterableEquals(Collections.singleton(child), actual);
    }
}