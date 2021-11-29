package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import static com.meti.Application.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    @Test
    void creates_main() {
        run();
        assertTrue(Files.exists(MainDirectory));
    }

    private void run() {
        try {
            new Application().run();
        } catch (ApplicationException e) {
            fail(e);
        }
    }

    @Test
    void creates_source() {
        run();
        assertTrue(Files.exists(SourceDirectory));
    }

    @Test
    void creates_test() {
        run();
        assertTrue(Files.exists(TestDirectory));
    }

    @Test
    void does_not_write_target() {
        run();
        assertFalse(Files.exists(OutDirectory.resolve("Test.h")));
    }

    private void runImpl(final String name, String input, String output) throws IOException {
        new com.meti.Path(MainDirectory).ensureDirectory();
        Files.writeString(MainDirectory.resolve(name + ".mgf"), input);
        run();
        assertEquals(output, Files.readString(OutDirectory.resolve(name + ".h")));
    }

    @Test
    void writes_another_target() throws IOException {
        assertWritesTarget("First");
    }

    private void assertWritesTarget(final String name) throws IOException {
        runImpl(name, "", "");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(SourceDirectory, new DeletingVisitor());
        Files.walkFileTree(Root.resolve("out"), new DeletingVisitor());
    }

    @Test
    void writes_content() throws IOException {
        assertSource("struct String {}", "struct String {};");
    }

    private void assertSource(String input, String output) throws IOException {
        runImpl("Test", input, output);
    }

    @Test
    void writes_other_content() throws IOException {
        assertSource("struct Test {}", "struct Test {};");
    }

    @Test
    void writes_sub_directory() throws IOException {
        ensureFile(MainDirectory.resolve("com").resolve("Test.mgf"));
        run();
        assertTrue(Files.exists(OutDirectory.resolve("com").resolve("Test.h")));
    }

    private static void ensureFile(Path path) throws IOException {
        var parent = path.getParent();
        if (parent != null) new com.meti.Path(parent).ensureDirectory();
        if (!Files.exists(path)) Files.createFile(path);
    }

    @Test
    void writes_target() throws IOException {
        assertWritesTarget("Test");
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
