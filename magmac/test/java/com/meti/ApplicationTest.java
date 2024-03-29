package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    public static final Path ROOT = Paths.get(".");
    public static final Path TARGET = ROOT.resolve("ApplicationTest.mgs");
    public static final Path SOURCE = ROOT.resolve("ApplicationTest.java");

    private static void run(Path source, String extension) throws IOException {
        if (Files.exists(source)) {
            var name = source.getFileName().toString();
            var index = name.indexOf('.');
            var without = name.substring(0, index);
            var target = source.resolveSibling(without + extension);
            if (!Files.exists(target)) Files.createFile(target);
        }
    }

    @Test
    void generatesNoTarget() throws IOException {
        run(SOURCE, ".mgs");
        assertFalse(Files.exists(TARGET));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TARGET);
        Files.deleteIfExists(SOURCE);
    }

    @Test
    void generatesTarget() throws IOException {
        Files.createFile(SOURCE);
        run(SOURCE, ".mgs");
        assertTrue(Files.exists(TARGET));
    }
}
