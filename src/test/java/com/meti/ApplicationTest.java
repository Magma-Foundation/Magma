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
    private static final Path TestDirectory = SourceDirectory.resolve("test").resolve("magma");
    private static final Path MainDirectory = SourceDirectory.resolve("main").resolve("magma");
    private static final Path OutDirectory = Root.resolve("out");

    @Test
    void creates_main() throws IOException {
        run();
        assertTrue(Files.exists(MainDirectory));
    }

    @Test
    void writes_another_target() throws IOException {
        assertWritesTarget("First");
    }

    private void assertWritesTarget(final String name) throws IOException {
        ensureDirectory(MainDirectory);
        Files.createFile(MainDirectory.resolve(name + ".mgf"));
        run();
        assertTrue(Files.exists(OutDirectory.resolve("c").resolve(name + ".c")));
    }

    private void ensureDirectory(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    @Test
    void creates_source() throws IOException {
        run();
        assertTrue(Files.exists(SourceDirectory));
    }

    @Test
    void creates_test() throws IOException {
        run();
        assertTrue(Files.exists(TestDirectory));
    }

    @Test
    void does_not_write_target() throws IOException {
        run();
        assertFalse(Files.exists(OutDirectory.resolve("c").resolve("Test.c")));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(SourceDirectory, new DeletingVisitor());
        Files.walkFileTree(OutDirectory, new DeletingVisitor());
    }

    private void run() throws IOException {
        ensureDirectory(SourceDirectory);
        ensureDirectory(MainDirectory);
        ensureDirectory(TestDirectory);

        var sourceFiles = Files.walk(MainDirectory).collect(Collectors.toSet());
        for (var relativeToMain : sourceFiles) {
            var relativeSource = MainDirectory.relativize(relativeToMain);

            var fileName = relativeToMain.getFileName().toString();
            var separator = fileName.indexOf('.');
            if (separator != -1) {
                var name = fileName.substring(0, separator);
                var extension = fileName.substring(separator + 1);
                if (extension.equals("mgf")) {
                    var parent = OutDirectory.resolve("c");
                    ensureDirectory(parent);
                    var relativeTarget = relativeSource.resolveSibling(name + ".c");
                    var relativeToTarget = parent.resolve(relativeTarget);
                    ensureFile(relativeToTarget);
                }
            }
        }
    }

    private void ensureFile(Path path) throws IOException {
        var parent = path.getParent();
        if (parent != null) ensureDirectory(parent);
        if (!Files.exists(path)) Files.createFile(path);
    }

    @Test
    void writes_sub_directory() throws IOException {
        ensureFile(MainDirectory.resolve("com").resolve("Test.mgf"));
        run();
        assertTrue(Files.exists(OutDirectory.resolve("c").resolve("com").resolve("Test.c")));
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
