package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    private static final Path Root = Paths.get(".");
    private static final Path SourceDirectory = Root.resolve("source");
    private static final Path OutDirectory = Root.resolve("out");

    @Test
    void creates_another_file() throws IOException {
        assertCreatesFile("test1");
    }

    private void assertCreatesFile(String name) throws IOException {
        createSourceDirectory();
        Files.createFile(resolveSourceTestFile(name));
        run();
        assertTrue(Files.exists(resolveOutputTestFile(name)));
    }

    private void createSourceDirectory() throws IOException {
        Files.createDirectory(SourceDirectory);
    }

    private Path resolveSourceTestFile(final String name) {
        return SourceDirectory.resolve(name + ".mgf");
    }

    private void run() throws IOException {
        var children = Files.list(SourceDirectory).collect(Collectors.toList());
        for (Path child : children) {
            var filePath = child.getFileName().toString();
            var separator = filePath.indexOf('.');
            var name = filePath.substring(0, separator);

            var path = resolveOutputTestFile(name);
            Files.createDirectory(path.getParent().getParent());
            Files.createDirectory(path.getParent());
            Files.createFile(path);
        }
    }

    private Path resolveOutputTestFile(final String name) {
        return OutDirectory.resolve("c").resolve(name + ".c");
    }

    @Test
    void creates_single_file() throws IOException {
        assertCreatesFile("test");
    }

    @Test
    void does_not_create_out_directory() {
        assertFalse(Files.exists(OutDirectory));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(OutDirectory, new DeletingVisitor());
        Files.walkFileTree(SourceDirectory, new DeletingVisitor());
    }

    private static class DeletingVisitor implements FileVisitor<Path> {
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }
    }
}
