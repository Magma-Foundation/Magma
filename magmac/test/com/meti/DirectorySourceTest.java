package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DirectorySourceTest {

    private Path directory;

    @BeforeEach
    void setUp() throws IOException {
        directory = Files.createTempDirectory("temp");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
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
    void collectIsEmpty() throws IOException {
        var actual = collectImpl();
        assertTrue(actual.isEmpty());
    }

    @Test
    void collectOnce() throws IOException {
        var expected = directory.resolve("Test.java");
        Files.createFile(expected);
        var actual = collectImpl();
        assertIterableEquals(Collections.singleton(expected), actual);
    }

    private Set<Path> collectImpl() throws IOException {
        return new DirectorySource(directory, "java").collectSources();
    }

    @Test
    void collectTwice() throws IOException {
        var expected = directory.resolve("Test.java");
        var expected1 = directory.resolve("Test1.java");
        Files.createFile(expected);
        Files.createFile(expected1);

        var actual = collectImpl();
        var expectedSet = Set.of(expected, expected1);
        assertTrue(expectedSet.containsAll(actual));
        assertTrue(actual.containsAll(expectedSet));
    }

    @Test
    void collectByExtension() throws IOException {
        var expected = directory.resolve("Test.foo");
        Files.createFile(expected);
        var actual = collectImpl();
        assertTrue(actual.isEmpty());
    }
}