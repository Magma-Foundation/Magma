package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    private static final Path Root = Paths.get(".");
    private static final Path SourceDirectory = Root.resolve("source");
    private static final Path OutDirectory = Root.resolve("out");

    private void createSourceDirectory() throws IOException {
        Files.createDirectory(SourceDirectory);
    }

    private void run() throws IOException {
        if (Files.exists(resolveSourceTestFile())) {
            var path = resolveOutputTestFile();
            Files.createDirectory(path.getParent().getParent());
            Files.createDirectory(path.getParent());
            Files.createFile(path);
        }
    }

    private Path resolveSourceTestFile() {
        return SourceDirectory.resolve("test.mgf");
    }

    private Path resolveOutputTestFile() {
        return OutDirectory.resolve("c").resolve("test.c");
    }

    @Test
    void creates_single_file() throws IOException {
        createSourceDirectory();
        Files.createFile(resolveSourceTestFile());

        run();

        assertTrue(Files.exists(resolveOutputTestFile()));
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
