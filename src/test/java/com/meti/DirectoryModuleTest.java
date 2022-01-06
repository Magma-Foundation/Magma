package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class DirectoryModuleTest {
    private static final Path Parent = NIOPath.Root.resolveChild("parent");

    @Test
    void listSources() throws IOException {
        var child = Parent.resolve("child.mgs");

        Files.createDirectory(Parent);
        Files.createFile(child);

        var sources = new DirectoryModule(Parent).listSources();
        assertIterableEquals(Collections.singletonList(child), sources);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(Parent, new FileVisitor<>() {
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
        });
    }
}