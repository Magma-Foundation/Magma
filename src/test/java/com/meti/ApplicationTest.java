package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    private static final Path SourceDirectoryPath = Paths.get(".", "source");
    private static final Path TestDirectoryPath = SourceDirectoryPath.resolve("test").resolve("magma");
    private static final Path MainDirectoryPath = SourceDirectoryPath.resolve("main").resolve("magma");
    private static final Path OutDirectory = Paths.get(".", "out");

    @Test
    void creates_main() throws IOException {
        run();
        assertTrue(Files.exists(MainDirectoryPath));
    }

    @Test
    void creates_source() throws IOException {
        run();
        assertTrue(Files.exists(SourceDirectoryPath));
    }

    private void run() throws IOException {
        ensure(SourceDirectoryPath);
        ensure(MainDirectoryPath);
        ensure(TestDirectoryPath);

        if (Files.exists(MainDirectoryPath.resolve("Test.mgf"))) {
            var parent = OutDirectory.resolve("c");
            ensure(parent);
            var resolve = parent.resolve("Test.c");
            if (!Files.exists(resolve)) {
                Files.createFile(resolve);
            }
        }
    }

    private void ensure(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    @Test
    void creates_test() throws IOException {
        run();
        assertTrue(Files.exists(TestDirectoryPath));
    }

    @Test
    void does_not_write_target() throws IOException {
        run();
        assertFalse(Files.exists(OutDirectory.resolve("c").resolve("Test.c")));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(SourceDirectoryPath, new DeletingVisitor());
        Files.walkFileTree(OutDirectory, new DeletingVisitor());
    }

    @Test
    void writes_target() throws IOException {
        ensure(MainDirectoryPath);
        Files.createFile(MainDirectoryPath.resolve("Test.mgf"));
        run();
        assertTrue(Files.exists(OutDirectory.resolve("c").resolve("Test.c")));
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
