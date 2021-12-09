package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    private void assertCreatesFile(String name) throws IOException {
        createSourceDirectory();
        createSourceTestFile(name);
        Application.run();
        assertTrue(Files.exists(Application.OutputCDirectory.resolve(name + ".c")));
    }

    private void createSourceDirectory() throws IOException {
        Files.createDirectory(Application.SourceDirectory);
    }

    private void createSourceTestFile(String name) throws IOException {
        Files.createFile(resolveSourceTestFile(name));
    }

    @Test
    void creates_another_file() throws IOException {
        assertCreatesFile("test1");
    }

    @Test
    void creates_files() throws IOException {
        createSourceDirectory();
        createSourceTestFile("first");
        createSourceTestFile("second");

        Application.run();

        var expected = Set.of("first.c", "second.c");
        var actual = Files.list(Application.OutputCDirectory)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    @Test
    void creates_package() throws IOException {
        var parent = Application.SourceDirectory.resolve("inner");
        Files.createDirectories(parent);
        Files.createFile(parent.resolve("test.mgf"));

        Application.run();

        assertTrue(Files.exists(Application.OutputCDirectory.resolve("inner").resolve("test.c")));
    }

    @Test
    void creates_single_file() throws IOException {
        assertCreatesFile("test");
    }

    @Test
    void does_not_create_out_directory() {
        assertFalse(Files.exists(Application.OutDirectory));
    }

    private Path resolveSourceTestFile(final String name) {
        return Application.SourceDirectory.resolve(name + ".mgf");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(Application.OutDirectory, new DeletingVisitor());
        Files.walkFileTree(Application.SourceDirectory, new DeletingVisitor());
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
