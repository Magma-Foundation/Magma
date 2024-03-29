package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

class DirectorySourceSetTest {
    public static final Path ROOT = Paths.get(".", "temp");

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(ROOT, new DeletingVisitor());
    }

    @Test
    void collect() throws IOException {
        Files.createDirectory(ROOT);

        var child = ROOT.resolve("test.txt");
        Files.createFile(child);

        Assertions.assertTrue(new DirectorySourceSet(ROOT)
                .collect()
                .stream()
                .map(PathSource::location)
                .collect(Collectors.toSet())
                .contains(child));
    }
}